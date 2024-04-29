package com.liangshou.llmsrefactor.codedata.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Nullable;

import java.time.Instant;
import java.util.UUID;

/**
 * @author X-L-S
 */
public record CodeData (String id,
                       String fileName,
                       String languageType,
                       String originCode,
                       @Nullable String originReport,
                       @Nullable Integer originNumProblem,
                       @Nullable String newCode,
                       @Nullable String newReport,
                       @Nullable Integer newNumProblem,
                       @Nullable Boolean isSame,
                       Instant createAt,
                       Instant updateAt){
    @JsonCreator
    public CodeData(
            @JsonProperty("fileName") String fileName,
            @JsonProperty("languageType")String languageType,
            @JsonProperty("languageType")String originCode,
            @Nullable @JsonProperty("languageType")String originReport,
            @Nullable @JsonProperty("languageType")Integer originNumProblem,
            @Nullable @JsonProperty("languageType")String newCode,
            @Nullable @JsonProperty("languageType")String newReport,
            @Nullable @JsonProperty("languageType")Integer newNumProblem,
            @Nullable @JsonProperty("isSame") Boolean isSame
    ){
        this(UUID.randomUUID().toString(),
                fileName,
                languageType,
                originCode,
                originReport,
                originNumProblem,
                newCode,
                newReport,
                newNumProblem,
                isSame,
                Instant.now(),
                Instant.now());
    }
}
