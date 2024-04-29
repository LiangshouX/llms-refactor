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
               TILE, article.title(),
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
        String title = (String) metadata.getOrDefault(TILE, "");
        return new KnowledgeBaseArticle(
               document.getId(),
                category,
                title,
                document.getContent(),
                parseTimestamp(metadata, CREATED_AT),
                parseTimestamp(metadata, UPDATED_AT)
        );
    }

    private static String parseTimestamp(Map<String, Object> metadata,
                                          String key) {
        String value = (String) metadata.getOrDefault(key, "0");
        try {
            return Instant.ofEpochSecond(Long.parseLong(value), 0).toString();
        } catch (NumberFormatException e) {
            return Instant.now().toString();
        }
    }
}
