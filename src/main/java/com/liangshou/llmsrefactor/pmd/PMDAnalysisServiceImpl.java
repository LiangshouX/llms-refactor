package com.liangshou.llmsrefactor.pmd;

import net.sourceforge.pmd.PMDConfiguration;
import net.sourceforge.pmd.PmdAnalysis;
import net.sourceforge.pmd.reporting.Report;
import net.sourceforge.pmd.reporting.RuleViolation;
import org.springframework.stereotype.Service;

import java.nio.file.Paths;
import java.util.List;

import static com.liangshou.llmsrefactor.pmd.constant.PMDConfigConstant.RULESET_HTML_PATH;
import static com.liangshou.llmsrefactor.pmd.constant.PMDConfigConstant.RULESET_JAVA_PATH;

/**
 * @author X-L-S
 */
@Service
public class PMDAnalysisServiceImpl implements PMDAnalysisService {
    @Override
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
            default:
                config.addRuleSet(RULESET_JAVA_PATH);
        }

        config.setReportFormat("json");
        return config;
    }

    @Override
    public List<RuleViolation> analyzeCode(PMDConfiguration config, String fileRoot, String fileName) {
        try (PmdAnalysis pmd = PmdAnalysis.create(config)){
            pmd.files().addFile(Paths.get(fileRoot, fileName));
            Report report = pmd.performAnalysisAndCollectReport();
            System.out.println("==================");
            System.out.println(report.getViolations());
            System.out.println(report.getConfigurationErrors());
            System.out.println(report.getSuppressedViolations());
            return report.getViolations();
        }catch (Exception e){
            // e.printStackTrace();
            return null;
        }

    }
}
