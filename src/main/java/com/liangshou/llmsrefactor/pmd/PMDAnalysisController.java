package com.liangshou.llmsrefactor.pmd;

import jakarta.annotation.Resource;
import net.sourceforge.pmd.PMDConfiguration;
import net.sourceforge.pmd.reporting.RuleViolation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.liangshou.llmsrefactor.pmd.constant.PMDConfigConstant.CODE_ROOT;

/**
 * @author X-L-S
 */
@RestController
public class PMDAnalysisController {
    @Resource
    private PMDAnalysisService pmdAnalysisService;

    @GetMapping("/analysis")
    public List<RuleViolation> doAnalysis(){
        PMDConfiguration config = pmdAnalysisService.createPmdConfig("java");
        String fileName = "SystemProxyConfig.java";
        return pmdAnalysisService.analyzeCode(config, CODE_ROOT, fileName);
    }
}
