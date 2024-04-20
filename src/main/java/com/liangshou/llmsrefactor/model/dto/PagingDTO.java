package com.liangshou.llmsrefactor.model.dto;

import lombok.Data;

/**
 * @author X-L-S
 */
@Data
public class PagingDTO {
    /**
     * 页面索引
     */
    private Integer pageIndex;
    /**
     * 页面大小
     */
    private Integer pageSize;
    /**
     * 总
     */
    private Integer total;
}
