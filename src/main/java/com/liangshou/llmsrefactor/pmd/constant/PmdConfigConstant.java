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

    String JAVA_CODE_ROOT = CODE_ROOT + "java/";

    String JAVA_CODE_PATH = JAVA_CODE_ROOT + "raw/";

    String JAVA_CODE_CLASS_PATH = JAVA_CODE_ROOT + "raw/class/";

    String HTML_CODE_ROOT = CODE_ROOT + "html/";
}