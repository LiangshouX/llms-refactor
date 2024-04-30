package com.liangshou.llmsrefactor.codedata;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @author X-L-S
 */
@RestController
@RequestMapping("/api/v1/code")
public class CodeDataController {

    private final CodeDataService codeDataService;

    public CodeDataController(CodeDataService codeDataService){
        this.codeDataService = codeDataService;
    }

    @GetMapping("/save-all")
    public void saveCodeData(@RequestParam(required = true, defaultValue = "java") String langugeType) throws IOException {
        codeDataService.saveFilesToDb(langugeType);
    }
}
