package com.liangshou.llmsrefactor.knowlefgebase.entity;

import java.util.List;

/**
 * @author X-L-S
 */
public record Categories (List<Category> data, long total){
    public Categories(List<Category> data){
        this(data, data.size());
    }
}
