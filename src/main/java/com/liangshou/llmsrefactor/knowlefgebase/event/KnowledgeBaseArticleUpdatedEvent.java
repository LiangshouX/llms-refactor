package com.liangshou.llmsrefactor.knowlefgebase.event;

import com.liangshou.llmsrefactor.model.entity.KnowledgeBaseArticle;
import org.springframework.context.ApplicationEvent;

import java.awt.desktop.AppEvent;

/**
 * @author X-L-S
 */
public class KnowledgeBaseArticleUpdatedEvent extends ApplicationEvent {

    public KnowledgeBaseArticleUpdatedEvent(KnowledgeBaseArticle source){
        super(source);
    }
}
