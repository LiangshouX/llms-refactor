package com.liangshou.llmsrefactor.model.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * 描述一个代码文件的实体类
 * @author X-L-S
 */
@Setter
@Getter
public class CodeFile {
    /**
     * 代码文件的 ID
     */
    private int id;

    /**
     * 代码文件的文件名
     */
    private String fileName;

    /**
     * 代码文件的内容
     */
    private String code;
}
