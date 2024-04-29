package com.liangshou.llmsrefactor.pmd.entity;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

/**
 * @author X-L-S
 */
public record PmdViolations (String fileName, List<PmdViolation> violations, int total){

    public PmdViolations(String fileName, List<PmdViolation> violations) {
        this(fileName, violations, violations.size());
    }

    /**
     * 将此类的属性转换为 Json 串
     *
     * @return String Json 串
     */
    public String toJson(){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(this);
        } catch ( Exception e ) {

            throw new RuntimeException("Failed to convert PmdViolations to JSON", e);
        }
    }
}
