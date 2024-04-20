package com.liangshou.llmsrefactor.common;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 删除请求
 *
 */
@Data
public class DeleteRequest implements Serializable {
    private Long id;

    @Serial
    private static final long serialVersionUID = 1L;
}
