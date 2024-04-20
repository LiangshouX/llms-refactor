package com.liangshou.llmsrefactor.knowlefgebase.entity;

import java.util.List;

/**
 * @author X-L-S
 */
public record Articles(List<Article> data, long total) {

    public Articles(List<Article> data){
        this(data, data.size());
    }
}
