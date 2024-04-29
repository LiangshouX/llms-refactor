package com.liangshou.utils;

import com.liangshou.llmsrefactor.utils.LoadPromptUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author X-L-S
 */
@Deprecated
@SpringBootTest
class LoadPromptUtilTest {
    @Test
    void loadPromptTest(){
        System.out.println(LoadPromptUtil.loadPrompt("0.system-prompt.txt"));
    }
}