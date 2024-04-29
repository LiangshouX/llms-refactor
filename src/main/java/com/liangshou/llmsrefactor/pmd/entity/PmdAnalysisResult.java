package com.liangshou.llmsrefactor.pmd.entity;

/**
 * <pre>
 *     用于封装 PMD 分析结果的实体类. 此类仅关注 “files” 字段下的结果
 *     考虑到是 类级别 / 文件级别 的重构，files 列表的长度仅为 1
 * </pre>
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
 *
 * @author X-L-S
 */
@Deprecated
public record PmdAnalysisResult (String fileName,
                                 PmdViolations violations,
                                 long total) {
}
