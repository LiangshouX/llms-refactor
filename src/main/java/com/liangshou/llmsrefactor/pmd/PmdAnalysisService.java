package com.liangshou.llmsrefactor.pmd;

import com.liangshou.llmsrefactor.codedata.CodeDataRepository;
import com.liangshou.llmsrefactor.codedata.entity.CodeDataEntity;
import com.liangshou.llmsrefactor.pmd.entity.PmdAnalysisResult;
import com.liangshou.llmsrefactor.pmd.entity.PmdViolation;
import com.liangshou.llmsrefactor.pmd.entity.PmdViolations;
import net.sourceforge.pmd.PMDConfiguration;
import net.sourceforge.pmd.PmdAnalysis;
import net.sourceforge.pmd.reporting.Report;
import net.sourceforge.pmd.reporting.RuleViolation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.sql.SQLException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static com.liangshou.llmsrefactor.pmd.constant.PmdConfigConstant.*;

/**
 * DBMSMetadata 封装了 JDBC 连接以供 PMD 使用
 * <p>
 *      DBURI参数指定要传递给PMD的源代码。
 * <p>
 *     DBURI 初始化有两种方式：String 和 URI，本质都是 JDBC 的URL
 * <p>
 *     但是 PMD 封装的 JDBC 极其难用且不稳定，所以不采用 JDBC 操作，
 *     将代码保存到本地，通过 Path 引用
 *
 * @author X-L-S
 */
@Service
public class PmdAnalysisService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final CodeDataRepository codeDataRepository;

    private final ResourceLoader resourceLoader;

    public PmdAnalysisService(CodeDataRepository codeDataRepository,
                              ResourceLoader resourceLoader) throws
            SQLException, URISyntaxException, IOException, ClassNotFoundException {
        this.codeDataRepository = codeDataRepository;
        this.resourceLoader = resourceLoader;
    }


    /**
     * TODO: 检测单个的代码片段
     *
     * @param codeSnippet 代码片段
     */
    public void checkCodeSnippet (String codeSnippet) {

    }

    public PMDConfiguration createPmdConfig(String languageType) {
        PMDConfiguration config = new PMDConfiguration();
        // 添加对应语言的 ruleset 路径
        switch (languageType){
            case "java":
                config.addRuleSet(RULESET_JAVA_PATH);
                break;
            case "html":
                config.addRuleSet(RULESET_HTML_PATH);
                break;
                // TODO: 添加更多语言
            default:
                logger.error("Language Type {} not support！",languageType);
                // throw new RuntimeException("Language Type not support！");
        }
        // config.setShowSuppressedViolations(true);
        config.setReportFormat("csv");
        return config;
    }

    /**
     * 扫描完整的代码数据目录，全部执行检测，返回检测结果
     *
     * @param config PMD检测器配置
     * @param languageType  语言的类型
     * @param isOrigin 是否为原始代码
     * @return 结果列表
     */
    public List<String> analyzePath (PMDConfiguration config, String languageType, boolean isOrigin) {

        String fileRoot;

        switch (languageType) {
            case "java":
                fileRoot = JAVA_CODE_ROOT;
                break;
            case "html":
                fileRoot = HTML_CODE_ROOT;
                break;
            default:
                logger.error("Language type {} is not surpportted! ", languageType);
                throw new RuntimeException();
        }

        String filePath = isOrigin? fileRoot + "raw/" : fileRoot + "refactored/";

        File dir = new File(filePath);

        List<String> results = new ArrayList<>();


        if (dir.exists() && dir.isDirectory()){
            File[] files = dir.listFiles();
            if (files != null){
                for (File file: files){
                    // 如果是文件
                    if (file.isFile()) {
                        // TODO: 执行检测并将结果存入数据库
                        String fileName = file.getName();
                        logger.info("Analyzing file {} ...", fileName);
                        PmdAnalysisResult analysisResult = analyzeCode(config, filePath, file.getName());

                        CodeDataEntity codeEntity = codeDataRepository.findByFileName(fileName).get();

                        if (isOrigin) {
                            codeEntity.setOriginReport(analysisResult.getResultJson());
                            codeEntity.setOriginNumProblem(analysisResult.getTotal());
                        }
                        else {
                            codeEntity.setNewReport(analysisResult.getResultJson());
                            codeEntity.setNewNumProblem(analysisResult.getTotal());
                        }

                        codeEntity.setUpdateAt(Instant.now());

                        codeDataRepository.save(codeEntity);
                    }
                    else {
                        System.out.println("%s is not file.".formatted(file));
                    }
                }
            }
            else{
                logger.warn("The directory {} is empty", dir);
            }
        }
        else {
            logger.warn("Invalid directory path: {]", dir.getAbsolutePath());
        }


        return results;
    }


    /**
     * 执行对单个代码文件的检测，返回值为解析为了 JSON 格式的字符串
     *
     * @param config PMD检测器配置
     * @param filePath 文件存放路径
     * @param fileName 文件名
     * @return JSON 格式的字符串
     */
    public PmdAnalysisResult analyzeCode(PMDConfiguration config, String filePath, String fileName) {

        PmdAnalysisResult analysisResult = new PmdAnalysisResult();

        try (PmdAnalysis pmd = PmdAnalysis.create(config)){
            pmd.files().addFile(Paths.get(filePath, fileName));
            Report report = pmd.performAnalysisAndCollectReport();

            PmdViolations violations = collectViolation(fileName, report.getViolations());

            analysisResult.setTotal(report.getViolations().size());

            analysisResult.setResultJson(parseAnalysisResultToJson(violations));

            // return parseAnalysisResultToJson(violations);
            return analysisResult;

        }catch (Exception e){
            logger.error("Analyse file {} failed int path {}", fileName, filePath);
            return null;
        }

    }

    /**
     * 收集整理 report.getViolations() 的内容，得到自己想要的输出
     * <p>
     *  RuleViolation 提供了获取内部属性的方法，直接调用
     *
     * @param ruleViolations Violation 列表
     */
    private PmdViolations collectViolation(String fileName, List<RuleViolation> ruleViolations) {
        List<PmdViolation> violationList = new ArrayList<>();

        for (RuleViolation ruleViolation : ruleViolations){
            Integer beginLine = ruleViolation.getBeginLine();
            Integer endLine = ruleViolation.getEndLine();
            String description = ruleViolation.getDescription();
            String rule = ruleViolation.getRule().getName();
            String ruleSet = ruleViolation.getRule().getRuleSetName();
            Integer priority = ruleViolation.getRule().getPriority().getPriority();

            PmdViolation pmdViolation = new PmdViolation(beginLine,endLine,description,rule,ruleSet,priority);
            violationList.add(pmdViolation);
        }
        return new PmdViolations(fileName, violationList);
    }


    /**
     * PMD 检测收集的结果 转换为 JSON 串
     *
     * @param pmdViolations 测试结构的表示
     */
    private String parseAnalysisResultToJson (PmdViolations pmdViolations) {
        try {
            return pmdViolations.toJson();
        } catch (Exception e) {
            logger.error("Failed to convert PmdViolations of {} to JSON", pmdViolations.fileName());
            return null;
        }

    }
}
