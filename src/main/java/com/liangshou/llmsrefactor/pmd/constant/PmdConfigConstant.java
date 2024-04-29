package com.liangshou.llmsrefactor.pmd.constant;

/**
 * 与 PMD 配置相关的常量
 * @author X-L-S
 */
public interface PmdConfigConstant {
    String RULESET_ROOT = "src/main/resources/pmd-rulesets/";

    String CODE_ROOT = "src/main/resources/codeData/";

    String CODE_REFACTOR_ROOT = "src/main/resources/codeData/refactored/";

    String RULESET_JAVA_PATH = RULESET_ROOT + "java-ruleset.xml";

    String RULESET_HTML_PATH = RULESET_ROOT + "html-ruleset.xml";
}
