package com.liangshou.llmsrefactor.document.model;

import com.liangshou.llmsrefactor.common.UUIDs;
import com.liangshou.llmsrefactor.knowlefgebase.entity.KnowledgeBaseArticle;
import org.springframework.ai.document.Document;

import java.time.Instant;
import java.util.Map;

import static com.liangshou.llmsrefactor.document.model.DocumentFieldNames.*;

/**
 * @author X-L-S
 */
public class Documents {

    public static Document fromArticle(KnowledgeBaseArticle article) {
       Map<String, Object> metadata = Map.of(
               CATEGORY, article.category(),
               SUB_CATEGORY, article.subCategory(),
               ARTICLE_ID, UUIDs.normalize(article.id()),
               CREATED_AT, article.createAt(),
               UPDATED_AT, article.updateAt()
       );

       return new Document(article.content(), metadata);
    }

    public static KnowledgeBaseArticle fromDocument(Document document) {
        var metadata = document.getMetadata();
        String category = (String) metadata
                .getOrDefault(CATEGORY, "");
        String subCategory = (String) metadata.getOrDefault(SUB_CATEGORY, "");
        String itemId = (String) metadata.getOrDefault(ITEM_ID, "");
        String positiveExample = (String) metadata.getOrDefault(POSITIVE_EXAMPLE, "");
        String counterExample = (String) metadata.getOrDefault(COUNTER_EXAMPLE, "");
        String note = (String) metadata.getOrDefault(NOTE, "");
        return new KnowledgeBaseArticle(
               document.getId(),
                category,
                subCategory,
                itemId,
                document.getContent(),
                parseTimestamp(metadata, CREATED_AT),
                parseTimestamp(metadata, UPDATED_AT)
        );
    }

    private static Instant parseTimestamp(Map<String, Object> metadata,
                                          String key) {
        String value = (String) metadata.getOrDefault(key, "0");
        try {
            return Instant.ofEpochSecond(Long.parseLong(value), 0);
        } catch (NumberFormatException e) {
            return Instant.now();
        }
    }
}
