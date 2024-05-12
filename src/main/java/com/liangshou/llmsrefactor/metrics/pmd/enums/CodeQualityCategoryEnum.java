package com.liangshou.llmsrefactor.metrics.pmd.enums;

import lombok.Getter;

/**
 * 用来表示代码质量属性类别的枚举类，如性能(Performance)、复杂度(Complexity)等。
 *
 * @author X-L-S
 */
@Getter
public enum CodeQualityCategoryEnum {
    PERFORMANCE("性能", "代码执行的效率"),
    COMPLEXITY("复杂度", "代码结构的复杂程度"),
    COUPLING("耦合度", "模块间相互依赖的程度"),
    COHESION("内聚性", "模块内部元素之间的关联程度"),
    READABILITY("可读性", "代码易于理解的程度"),
    REUSABILITY("可复用性", "代码重复使用的容易程度");

    private String attribute;
    private String description;

    // 私有构造函数，用于初始化枚举实例
    CodeQualityCategoryEnum(String attribute, String description) {
        this.attribute = attribute;
        this.description = description;
    }
}
