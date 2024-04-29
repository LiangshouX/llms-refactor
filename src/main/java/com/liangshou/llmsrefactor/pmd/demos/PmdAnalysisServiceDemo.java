package com.liangshou.llmsrefactor.pmd.demos;

import net.sourceforge.pmd.PMDConfiguration;
import net.sourceforge.pmd.PmdAnalysis;
import net.sourceforge.pmd.renderers.JsonRenderer;
import net.sourceforge.pmd.renderers.Renderer;
import net.sourceforge.pmd.reporting.Report;

import java.nio.file.Paths;

import static com.liangshou.llmsrefactor.pmd.constant.PmdConfigConstant.CODE_ROOT;
import static com.liangshou.llmsrefactor.pmd.constant.PmdConfigConstant.RULESET_JAVA_PATH;

/**
 * @author X-L-S
 */
public class PmdAnalysisServiceDemo {

    private static PMDConfiguration setPmdConfig(){
        PMDConfiguration config = new PMDConfiguration();
        config.addRuleSet(RULESET_JAVA_PATH);   // 添加 ruleset
        config.setReportFormat("csv");
        return config;
    }

    public static void main(String[] args) {
        PMDConfiguration config = setPmdConfig();


        try (PmdAnalysis pmd = PmdAnalysis.create(config)){
            // note: don't use `config` once a PmdAnalysis has been created.
            // optional: add more rulesets
            // pmd.addRuleSet(pmd.newRuleSetLoader().loadFromResource(RULESET_JAVA_PATH));
            // optional: add more files
            pmd.files().addFile(Paths.get(CODE_ROOT, "SystemProxyConfig.java"));
            // optional: add more renderers
            Renderer renderer = new JsonRenderer();
            // pmd.addRenderer(renderer);
            Report report = pmd.performAnalysisAndCollectReport();
            System.out.println("===========================================");
            System.out.println(report.getViolations().getClass());
            System.out.println("===========================================");
            System.out.println(pmd.getReporter().numErrors());
            System.out.println(report.getSuppressedViolations());
            System.out.println("===========================================");
            System.out.println(report.getProcessingErrors());
        }
    }
}
