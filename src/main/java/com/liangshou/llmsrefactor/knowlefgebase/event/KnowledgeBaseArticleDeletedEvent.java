package com.liangshou.llmsrefactor.knowlefgebase.event;

import com.liangshou.llmsrefactor.knowlefgebase.entity.KnowledgeBaseArticle;
import org.springframework.context.ApplicationEvent;

/**
 * @author X-L-S
 */
public class KnowledgeBaseArticleDeletedEvent extends ApplicationEvent {

    public KnowledgeBaseArticleDeletedEvent(KnowledgeBaseArticle source){
        super(source);
    }
}
