package com.liangshou.llmsrefactor.document.loader;

import com.google.common.util.concurrent.RateLimiter;
import com.liangshou.llmsrefactor.common.UUIDs;
import com.liangshou.llmsrefactor.config.RedisConfig;
import com.liangshou.llmsrefactor.document.model.Documents;
import com.liangshou.llmsrefactor.knowlefgebase.entity.KnowledgeBaseArticle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.util.CollectionUtils;
import redis.clients.jedis.JedisPooled;
import redis.clients.jedis.search.Query;

import java.util.List;

import static com.liangshou.llmsrefactor.document.model.DocumentFieldNames.ARTICLE_ID;
import static com.liangshou.llmsrefactor.document.model.DocumentFieldNames.DEFAULT_PREFIX;

/**
 * @author X-L-S
 */
public class DocumentLoader {

    private final TextSplitter textSplitter;
    private final VectorStore vectorStore;
    private final JedisPooled jedisPooled;
    private final RedisConfig redisConfig;

    private final RateLimiter rateLimiter = RateLimiter.create(10);

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public DocumentLoader(TextSplitter textSplitter, VectorStore vectorStore,
                          JedisPooled jedisPooled, RedisConfig redisConfig) {
        this.textSplitter = textSplitter;
        this.vectorStore = vectorStore;
        this.jedisPooled = jedisPooled;
        this.redisConfig = redisConfig;
    }

    public void loadArticle(KnowledgeBaseArticle article, boolean update){
        if(update){
            deleteDocumentsByArticle(article);
        }
        var transformed = textSplitter.apply(List.of(Documents.fromArticle(article)));

        transformed = transformed.stream().map(
                document -> new Document(
                        document.getId(),
                        "%s\r\n\r\n%s".formatted(article.category(), document.getContent()),
                        document.getMetadata())
                )
                .toList();
        addDocuments(transformed);
        logger.info("Add {} documents for article {}", transformed.size(), article.id());
    }

    public void importArticles(List<KnowledgeBaseArticle> articles) {
        logger.info("Importing {} articles", articles.size());
        for (KnowledgeBaseArticle article : articles) {
            loadArticle(article, false);
        }
        logger.info("Imported {} articles", articles.size());
    }

    /**
     * 根据 article 从向量数据库中删除 document
     *
     * @param article Article对象
     */
    public void deleteDocumentsByArticle(KnowledgeBaseArticle article){
        var query = new Query(
                "@%s:%s".formatted(ARTICLE_ID, UUIDs.normalize(article.id()))
        ).dialect(2);

        var result = jedisPooled.ftSearch(redisConfig.indexName(), query);
        var docIds = result.getDocuments()
                .stream()
                .map(doc -> doc.getId().substring(DEFAULT_PREFIX.length()))
                .toList();

        logger.info("Found {} existing documents for article {}", docIds.size(), article.id());

        if (!CollectionUtils.isEmpty(docIds)) {
            vectorStore.delete(docIds);
        }
    }

    /**
     * 往向量数据库中添加 Document
     * @param documents document对象列表
     */
    private void addDocuments(List<Document> documents){
        rateLimiter.acquire();
        vectorStore.add(documents);
    }
}
