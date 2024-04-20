package com.liangshou.llmsrefactor.model.enums;

/**
 * 与模型的对话历史。list中的每个元素形式为 {“role”：角色, "content":内容}
 * <p>
 * role 枚举值定义
 */
public enum RoleEnum {
    USER("user"),
    ASSISTANT("assistant"),
    BOT("bot"),
    SYSTEM("system"),
    ATTACHMENT("attachment")
    ;

    private final String value;

    RoleEnum(String value) {
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }
}
