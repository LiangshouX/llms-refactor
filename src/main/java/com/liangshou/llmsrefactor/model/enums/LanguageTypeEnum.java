package com.liangshou.llmsrefactor.model.enums;

import lombok.Getter;

/**
 * 编程语言类型的枚举
 *
 * @author X-L-S
 */
@Getter
public enum LanguageTypeEnum {
    JAVA("java"),
    CPP("cpp"),
    PYTHON("py")
    ;

    private final String value;
    LanguageTypeEnum(String value){
        this.value = value;
    }
}
