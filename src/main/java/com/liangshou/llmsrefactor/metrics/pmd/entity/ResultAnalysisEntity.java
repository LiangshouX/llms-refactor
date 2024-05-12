package com.liangshou.llmsrefactor.metrics.pmd.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * 对结果进行分析的封装实体类表示
 *
 * @author X-L-S
 */

public record ResultAnalysisEntity(int totalProblems, Map<String, Integer> ruleSetCounts,
                                   Map<Integer, Integer> priorityCounts) {
}
