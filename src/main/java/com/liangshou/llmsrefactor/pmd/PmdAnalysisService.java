package com.liangshou.llmsrefactor.pmd;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.liangshou.llmsrefactor.codedata.CodeDataRepository;
import com.liangshou.llmsrefactor.pmd.entity.PmdAnalysisResult;
import com.liangshou.llmsrefactor.pmd.entity.PmdViolation;
import com.liangshou.llmsrefactor.pmd.entity.PmdViolations;
import net.sourceforge.pmd.PMDConfiguration;
import net.sourceforge.pmd.PmdAnalysis;
import net.sourceforge.pmd.reporting.Report;
import net.sourceforge.pmd.reporting.RuleViolation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.liangshou.llmsrefactor.pmd.constant.PmdConfigConstant.RULESET_HTML_PATH;
import static com.liangshou.llmsrefactor.pmd.constant.PmdConfigConstant.RULESET_JAVA_PATH;

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

    public PmdAnalysisService(CodeDataRepository codeDataRepository) throws
            SQLException, URISyntaxException, IOException, ClassNotFoundException {
        this.codeDataRepository = codeDataRepository;
    }


    /**
     * TODO: 检测单个的代码文件
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


    public String analyzeCode(PMDConfiguration config, String fileRoot, String fileName) {

        try (PmdAnalysis pmd = PmdAnalysis.create(config)){
            pmd.files().addFile(Paths.get(fileRoot, fileName));
            Report report = pmd.performAnalysisAndCollectReport();

            PmdViolations violations = collectViolation(fileName, report.getViolations());

            return parseAnalysisResultToJson(violations);

        }catch (Exception e){
            logger.error("Analyse file {} failed int path {}", fileName, fileRoot);
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
