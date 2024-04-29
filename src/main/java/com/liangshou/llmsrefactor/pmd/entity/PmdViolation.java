package com.liangshou.llmsrefactor.pmd.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 用于封装 PMD 分析结果的中的 violation 字段的实体类.
 * <p>
 *  <h2>PMD 分析的输出示例</h2>
 *  <pre>{@code
 *  {
 *   "formatVersion": 0,
 *   "pmdVersion": "7.0.0",
 *   "timestamp": "2024-04-25T14:14:07.695+08:00",
 *   "files": [
 *     {
 *       "filename": "src\\main\\resources\\codeData\\HttpClientUtil.java",
 *       "violations": [
 *         {
 *           "beginline": 35,
 *           "begincolumn": 8,
 *           "endline": 35,
 *           "endcolumn": 13,
 *           "description": "This utility class has a non-private constructor",
 *           "rule": "UseUtilityClass",
 *           "ruleset": "Design",
 *           "priority": 3,
 *           "externalInfoUrl": "https://docs.pmd-code.org/pmd-doc-7.0.0/pmd_rules_java_design.html#useutilityclass"
 *         }
 *       ]
 *     }
 *   ],
 *   "suppressedViolations": [],
 *   "processingErrors": [],
 *   "configurationErrors": []
 * }
 *  }</pre>

 * @author X-L-S
 */
public record PmdViolation (Integer beginLine,
                            Integer endLine,
                            String description,
                            String rule,
                            String ruleSet,
                            Integer priority) {

}
