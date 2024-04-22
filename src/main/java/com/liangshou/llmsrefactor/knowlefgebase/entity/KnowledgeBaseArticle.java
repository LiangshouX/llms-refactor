package com.liangshou.llmsrefactor.knowlefgebase.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Nullable;

import java.time.Instant;
import java.util.UUID;

/**
 * 描述知识库的 Article 的实体类
 *
 * @author X-L-S
 */
public record KnowledgeBaseArticle(String id,
                                   String category,
                                   String subCategory,
                                   String itemId,
                                   String content,
                                   Instant createAt,
                                   Instant updateAt) {
    @JsonCreator
    public KnowledgeBaseArticle(
            @JsonProperty("category")String category,
            @JsonProperty("subCategory") String subCategory,
            @Nullable @JsonProperty("itemId") String itemId,
            @JsonProperty("content") String content
    ){
        this(UUID.randomUUID().toString(),
                category,
                subCategory,
                itemId,
                content,
                Instant.now(),
                Instant.now());
    }
}
