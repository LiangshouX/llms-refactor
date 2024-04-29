package com.liangshou.llmsrefactor.document.loader;

import com.liangshou.llmsrefactor.knowlefgebase.entity.KnowledgeBaseArticle;
import com.liangshou.llmsrefactor.knowlefgebase.event.KnowledgeBaseArticleCreatedEvent;
import com.liangshou.llmsrefactor.knowlefgebase.event.KnowledgeBaseArticleDeletedEvent;
import com.liangshou.llmsrefactor.knowlefgebase.event.KnowledgeBaseArticleUpdatedEvent;
import com.liangshou.llmsrefactor.knowlefgebase.event.KnowledgeBaseArticlesImportedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;

import java.util.List;

/**
 * 知识库事件处理类，包括导入、新建、删除、更新等几类事件
 *
 * @author X-L-S
 */
public class KnowledgeBaseEventHandler {
    private final DocumentLoader documentLoader;

    public KnowledgeBaseEventHandler(DocumentLoader documentLoader) {
        this.documentLoader = documentLoader;
    }

    @EventListener
    @Async
    public void handleArticleCreated(KnowledgeBaseArticleCreatedEvent event) {
        documentLoader.loadArticle((KnowledgeBaseArticle) event.getSource(), false);
    }

    @EventListener
    @Async
    public void handleArticleUpdated(KnowledgeBaseArticleUpdatedEvent event) {
        documentLoader.loadArticle((KnowledgeBaseArticle) event.getSource(), true);
    }

    @EventListener
    @Async
    public void handleArticleDeleted(KnowledgeBaseArticleDeletedEvent event) {
        documentLoader.deleteDocumentsByArticle(
                (KnowledgeBaseArticle) event.getSource());
    }

    @EventListener
    @Async
    public void handleArticlesImported(KnowledgeBaseArticlesImportedEvent event) {
        documentLoader.importArticles( (List<KnowledgeBaseArticle>) event.getSource() );
    }
}
