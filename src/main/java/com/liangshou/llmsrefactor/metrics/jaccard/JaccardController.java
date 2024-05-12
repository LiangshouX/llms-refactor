package com.liangshou.llmsrefactor.metrics.jaccard;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author X-L-S
 */
@RestController
public class JaccardController {
    @Resource
    JaccardService jaccardService;

    @GetMapping("/jaccard/show")
    public void showSummarySimilarities () {
        jaccardService.showSummarySimilarities();
    }
}
