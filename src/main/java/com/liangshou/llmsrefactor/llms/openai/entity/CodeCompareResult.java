package com.liangshou.llmsrefactor.llms.openai.entity;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 用于描述两段代码一致性对比结果的表示类
 *
 * @author X-L-S
 */
public record CodeCompareResult(String maintain,
                                String description) {
    public String toJsonString () {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(this);
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert CodeCompare to JSON", e);
        }
    }
}
