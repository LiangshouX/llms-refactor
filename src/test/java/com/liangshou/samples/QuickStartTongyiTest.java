package com.liangshou.samples;

import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author X-L-S
 */
@SpringBootTest
class QuickStartTongyiTest {
    @Resource
    QuickStartTongyi start;

    @Test
    void quickStart() {
        try {
            start.quickStart();
        } catch (NoApiKeyException | InputRequiredException e) {
            throw new RuntimeException(e);
        }
        System.exit(0);
    }
}