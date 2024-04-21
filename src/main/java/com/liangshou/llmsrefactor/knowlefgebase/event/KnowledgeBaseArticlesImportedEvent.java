package com.liangshou.llmsrefactor.knowlefgebase.event;

import com.liangshou.llmsrefactor.knowlefgebase.entity.KnowledgeBaseArticle;
import org.springframework.context.ApplicationEvent;

import java.util.List;

/**
 * @author X-L-S
 */
public class KnowledgeBaseArticlesImportedEvent extends ApplicationEvent {

    public KnowledgeBaseArticlesImportedEvent(List<KnowledgeBaseArticle> source){
        super(source);
    }
}
