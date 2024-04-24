package com.liangshou.llmsrefactor.vectorstore;

import com.liangshou.llmsrefactor.config.RedisConfig;
import com.liangshou.llmsrefactor.config.RedisSharedConfiguration;
import org.springframework.ai.embedding.EmbeddingClient;
import org.springframework.ai.vectorstore.RedisVectorStore;
import org.springframework.ai.vectorstore.RedisVectorStore.MetadataField;
import org.springframework.ai.vectorstore.RedisVectorStore.RedisVectorStoreConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import static com.liangshou.llmsrefactor.document.model.DocumentFieldNames.*;

/**
 * 向量存储模块配置类
 *
 * @author X-L-S
 */
@Configuration
@Import(RedisSharedConfiguration.class)
public class VectorStoreModuleConfiguration {

    @Bean
    public RedisVectorStore redisVectorStore(RedisConfig redisConfig,
                                             EmbeddingClient embeddingClient) {
        var config = RedisVectorStoreConfig.builder()
                .withURI(redisConfig.url())
                .withIndexName(redisConfig.indexName())
                .withMetadataFields(
                        MetadataField.tag(CATEGORY),
                        MetadataField.text(SUB_CATEGORY),
                        MetadataField.tag(ITEM_ID),
                        MetadataField.tag(ARTICLE_ID),
                        MetadataField.numeric(CREATED_AT),
                        MetadataField.numeric(UPDATED_AT)
                )
                .build();
        return new RedisVectorStore(config, embeddingClient);
    }
}
