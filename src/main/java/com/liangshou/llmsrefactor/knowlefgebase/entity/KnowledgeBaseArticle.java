package com.liangshou.llmsrefactor.knowlefgebase.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Nullable;

import java.time.Instant;
import java.util.UUID;

/**
 * 描述知识库的 Article 的实体类
 * @author X-L-S
 */
public record KnowledgeBaseArticle(String id,
                                   String category,
                                   String subCategory,
                                   String level,
                                   String content,
                                   Instant createAt,
                                   Instant updateAt) {
    @JsonCreator
    public KnowledgeBaseArticle(
            @JsonProperty("category")String category,
            @JsonProperty("subCategory") String subCategory,
            @JsonProperty("level") String level,
            @JsonProperty("content") String content
    ){
        this(UUID.randomUUID().toString(),
                category,
                subCategory,
                level,
                content,
                Instant.now(),
                Instant.now());
    }
}
