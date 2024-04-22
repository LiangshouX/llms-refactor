package com.liangshou.llmsrefactor.knowlefgebase;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author X-L-S
 */
@Configuration
@EnableAspectJAutoProxy
public class KnowledgeBaseModuleConfiguration {

    @Bean
    public KnowledgeBaseService knowledgeBaseService(KnowledgeBaseArticleRepository repository){
        return new KnowledgeBaseService(repository);
    }
}
