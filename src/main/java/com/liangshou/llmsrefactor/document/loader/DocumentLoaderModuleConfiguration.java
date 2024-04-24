package com.liangshou.llmsrefactor.document.loader;

import com.liangshou.llmsrefactor.config.RedisConfig;
import com.liangshou.llmsrefactor.config.RedisSharedConfiguration;
import com.liangshou.llmsrefactor.vectorstore.VectorStoreModuleConfiguration;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import redis.clients.jedis.JedisPooled;

/**
 * @author X-L-S
 */
@Configuration
@Import({
        VectorStoreModuleConfiguration.class,
        RedisSharedConfiguration.class
})
public class DocumentLoaderModuleConfiguration {
    @Bean
    public TextSplitter textSplitter() {
        return new RecursiveTextSplitter();
    }

    @Bean
    public DocumentLoader documentLoader(TextSplitter textSplitter,
                                         VectorStore vectorStore, JedisPooled jedisPooled,
                                         RedisConfig redisConfig) {
        return new DocumentLoader(textSplitter, vectorStore, jedisPooled,
                redisConfig);
    }

    @Bean
    public KnowledgeBaseEventHandler knowledgeBaseEventHandler(
            DocumentLoader documentLoader) {
        return new KnowledgeBaseEventHandler(documentLoader);
    }
}
