package com.liangshou.llmsrefactor.knowlefgebase.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;
import java.util.UUID;

/**
 * 描述知识库的 Article 的实体类
 *
 * @author X-L-S
 */
public record KnowledgeBaseArticle(String id,
                                   String category,
                                   String title,
                                   String content,
                                   String createAt,
                                   String updateAt) {
    @JsonCreator
    public KnowledgeBaseArticle(
            @JsonProperty("category")String category,
            @JsonProperty("title") String title,
            @JsonProperty("content") String content
    ){
        this(UUID.randomUUID().toString(),
                category,
                title,
                content,
                Instant.now().toString(),
                Instant.now().toString());
    }
}
