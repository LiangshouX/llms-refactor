package com.liangshou.llmsrefactor.model.entity;

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
                                   @Nullable String positiveExample,
                                   @Nullable String counterExample,
                                   @Nullable String note,
                                   Instant createAt,
                                   Instant updateAt) {
    @JsonCreator
    public KnowledgeBaseArticle(
            @JsonProperty("category")String category,
            @JsonProperty("subCategory") String subCategory,
            @JsonProperty("level") String level,
            @JsonProperty("content") String content,
            @Nullable@JsonProperty("positiveExample") String positiveExample,
            @Nullable@JsonProperty("counterExample") String counterExample,
            @Nullable@JsonProperty("note") String note
    ){
        this(UUID.randomUUID().toString(),
                category,
                subCategory,
                level,
                content,
                positiveExample,
                counterExample,
                note,
                Instant.now(),
                Instant.now());
    }
}
