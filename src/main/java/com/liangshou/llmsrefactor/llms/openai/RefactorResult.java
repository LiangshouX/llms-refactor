package com.liangshou.llmsrefactor.llms.openai;

/**
 * 记录 ChatGPT 重构代码后返回结果的 record
 *
 * @param refactoredCode 重构后的代码
 * @param explanation 对重构代码的简单解释
 * @author X-L-S
 */
public record RefactorResult (String refactoredCode,
                              String explanation) {
}
