package com.liangshou.llmsrefactor.metrics.pmd;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.liangshou.llmsrefactor.codedata.repository.CodeDataRepository;
import com.liangshou.llmsrefactor.codedata.entity.CodeDataEntity;
import com.liangshou.llmsrefactor.metrics.pmd.entity.PmdAnalysisResult;
import com.liangshou.llmsrefactor.metrics.pmd.entity.PmdViolation;
import com.liangshou.llmsrefactor.metrics.pmd.entity.PmdViolations;
import com.liangshou.llmsrefactor.metrics.pmd.entity.ResultAnalysisEntity;
import io.swagger.models.auth.In;
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
import java.sql.SQLException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.liangshou.llmsrefactor.metrics.pmd.constant.PmdConfigConstant.*;

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

    private final ObjectMapper objectMapper;

    public PmdAnalysisService(CodeDataRepository codeDataRepository,
                              ObjectMapper objectMapper) throws
            SQLException, URISyntaxException, IOException, ClassNotFoundException {
        this.codeDataRepository = codeDataRepository;
        this.objectMapper = objectMapper;
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
                        System.out.printf("%s is not file.%n", file);
                    }
                }
            }
            else{
                logger.warn("The directory {} is empty", dir);
            }
        }
        else {
            logger.warn("Invalid directory path: {}", dir.getAbsolutePath());
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

            PmdViolation pmdViolation = new PmdViolation(description,rule,ruleSet,priority);
            violationList.add(pmdViolation);
        }
        return new PmdViolations(fileName, violationList);
    }

    /**
     * 对所有的记录分析统计结果
     *
     */
    public ResultAnalysisEntity resultAnalysisAll (boolean isOrigin) {
        List<Integer> totalProblemCount = new ArrayList<>();

        int totalProblems = 0;
        Map<String, Integer> ruleSetCounts = new HashMap<>();
        Map<Integer,Integer> priorityCounts = new HashMap<>();

        for (long id = 1L; id <= 48; id++){
            ResultAnalysisEntity singleAnalysis = resultAnalysis(id, isOrigin);

            // 统计全部的问题数
            totalProblems += singleAnalysis.totalProblems();
            totalProblemCount.add(singleAnalysis.totalProblems());

            // 统计全部的 ruleset
            for (String key: singleAnalysis.ruleSetCounts().keySet()) {
                Integer value = singleAnalysis.ruleSetCounts().get(key);
                ruleSetCounts.put(key, ruleSetCounts.getOrDefault(key, 0) + value);
            }

            // 统计全部的 priority
            for (Integer key: singleAnalysis.priorityCounts().keySet()) {
                Integer value = singleAnalysis.priorityCounts().get(key);
                priorityCounts.put(key, priorityCounts.getOrDefault(key, 0) + value);
            }

            if (id % 5 == 0) {
                logger.info("前 {} 条记录的 {} 结果统计完成...", id, isOrigin?"原始":"重构后");
            }
        }
        System.out.println("Problem Count:\t" + totalProblemCount.toString());

        return new ResultAnalysisEntity(totalProblems, ruleSetCounts, priorityCounts);
    }

    /**
     * 对单条数据记录项的结果分析，分别分析原本的数据和重构后的数据
     * @param id 数据id
     */
    public ResultAnalysisEntity resultAnalysis (Long id, boolean isOrigin) {
        int totalProblems;
        Map<String, Integer> singleRuleSetCounts = new HashMap<>();
        Map<Integer,Integer> singlePriorityCounts = new HashMap<>();

        CodeDataEntity codeData = codeDataRepository.findById(id).get();

        String originReport = codeData.getOriginReport();
        String newReport = codeData.getNewReport();

        PmdViolations originViolations = parseJsonToAnalysisResult(originReport);
        PmdViolations newViolations = parseJsonToAnalysisResult(newReport);

        PmdViolations violations = isOrigin?originViolations:newViolations;

        try {
            if (violations == null){
                logger.info("CodeData {} has no {} violations, return null.", id, isOrigin?"origin":"new");
                return null;
            }

            totalProblems = violations.total();
            // 统计每一类 ruleset 的数量
            for (PmdViolation violation: violations.violations()) {
                singleRuleSetCounts.put(violation.ruleSet(),
                        singleRuleSetCounts.getOrDefault(violation.ruleSet(), 0) + 1);
            }
            // 统计每个 priority 的数量
            for (PmdViolation violation: violations.violations()){
                singlePriorityCounts.put(violation.priority(),
                        singlePriorityCounts.getOrDefault(violation.priority(), 0) + 1);
            }

            return new ResultAnalysisEntity(totalProblems, singleRuleSetCounts, singlePriorityCounts);

        } catch (Exception e){
            logger.error("Exception take place in resultAnalysis ", e);
            return null;
        }
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

    /**
     * 将 JSON 结果串转换成 PmdViolations 对象
     * @param json JSON结果
     */
    private PmdViolations parseJsonToAnalysisResult (String json) {
        try {
            return objectMapper.readValue(json, PmdViolations.class);
        }
        catch (Exception e) {
            logger.error("Exception take place when transfer json to PmdViolations object", e);
            return null;
        }
    }
}
