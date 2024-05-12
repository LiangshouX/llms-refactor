package com.liangshou.llmsrefactor.metrics.jaccard;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 将 String 代码串构建成为 HashSet 对象的工具类
 * @author X-L-S
 */
public class CodeToHashSet {
    private static final Pattern STRING_LITERAL_PATTERN = Pattern.compile("\"([^\"\\\\]*(\\\\.[^\"\\\\]*)*)\"");
    private static final Pattern CHAR_LITERAL_PATTERN = Pattern.compile("'([^'\\\\]*(\\\\.[^'\\\\]*)*)'");
    private static final Pattern COMMENT_PATTERN = Pattern.compile("//.*?$|/\\*.*?\\*/", Pattern.MULTILINE);

    /**
     * 更精确地将代码字符串转换为词汇HashSet，尝试处理字符串字面量、字符字面量及注释。
     * @param code 代码字符串
     * @return 词汇 HashSet
     */
    public static Set<String> convert(String code) {
        // 移除单行和多行注释
        Matcher commentMatcher = COMMENT_PATTERN.matcher(code);
        code = commentMatcher.replaceAll("");

        // 简化的状态机，用于跳过字符串字面量和字符字面量
        boolean inString = false;
        boolean inChar = false;
        StringBuilder cleanCodeBuilder = new StringBuilder();
        for (char c : code.toCharArray()) {
            if (c == '"' && !inChar) {
                inString = !inString;
            } else if (c == '\'' && !inString) {
                inChar = !inChar;
            } else if (!inString && !inChar) {
                cleanCodeBuilder.append(c);
            }
        }

        // 分割并去除非字母数字字符（可以根据需要调整）
        String[] words = cleanCodeBuilder.toString().split("\\W+");
        Set<String> codeSet = new HashSet<>();
        for (String word : words) {
            if (!word.isEmpty()) {
                codeSet.add(word);
            }
        }
        return codeSet;
    }
}
