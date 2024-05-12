package com.liangshou.llmsrefactor.metrics.pmd.constant;

/**
 * 与 PMD 配置相关的常量
 * 这些常量主要是位于 resources 目录下的路径，可避免 pmd 包相对路径的影响。
 * @author X-L-S
 */
public interface PmdConfigConstant {
    String ORIGIN = "origin";

    String NEW = "new";

    String RULESET_ROOT = "src/main/resources/pmd-rulesets/";

    String CODE_ROOT = "src/main/resources/codeData/";

    String CODE_REFACTOR_ROOT = "src/main/resources/codeData/refactored/";

    String RULESET_JAVA_PATH = RULESET_ROOT + "java-ruleset.xml";

    String RULESET_HTML_PATH = RULESET_ROOT + "html-ruleset.xml";

    String JAVA_CODE_ROOT = CODE_ROOT + "java/";

    String JAVA_CODE_PATH = JAVA_CODE_ROOT + "raw/";

    String JAVA_REFACTORED_PATH = JAVA_CODE_ROOT + "refactored/";

    String JAVA_CODE_CLASS_PATH = JAVA_CODE_ROOT + "raw/class/";

    String HTML_CODE_ROOT = CODE_ROOT + "html/";
}
