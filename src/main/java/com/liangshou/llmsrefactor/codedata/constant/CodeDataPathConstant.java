package com.liangshou.llmsrefactor.codedata.constant;

/**
 * 与代码文件路径相关的常量
 *
 * @author X-L-S
 */
public interface CodeDataPathConstant {
    // 代码文件存放的根目录
    String RESOURCE_ROOT = "codeData/";

    String JAVA_RESOURCE_ROOT = RESOURCE_ROOT + "java/";

    String JAVA_RAW_RESOURCE_PATH = JAVA_RESOURCE_ROOT + "raw/";

    String JAVA_REFACTORED_RESOURCE_PATH = JAVA_RESOURCE_ROOT + "refactored/";

    String HTML_RESOURCE_ROOT = RESOURCE_ROOT + "html/";

    String HTML_RAW_RESOURCE_PATH = HTML_RESOURCE_ROOT + "raw/";

    String HTML_REFACTOR_RESOURCE_PATH = HTML_RESOURCE_ROOT + "refactored/";
}
