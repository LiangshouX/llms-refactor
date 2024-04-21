package com.liangshou.llmsrefactor.knowlefgebase.entity;

import com.liangshou.llmsrefactor.knowlefgebase.request.CreateArticleRequest;
import com.liangshou.llmsrefactor.knowlefgebase.request.UpdateArticleRequest;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.time.Instant;
import java.util.UUID;
import lombok.Getter;


/**
 * 外部知识库的实体类。
 *
 * @author X-L-S
 */
@Entity
@Getter
public class Article {

    @Id
    UUID id;

    @Column(name = "category", nullable = false)
    String category;

    @Column(name = "sub_category", nullable = false)
    String subCategory;

    @Column(name = "level", nullable = false)
    String level;

    @Column(name = "content", nullable = false)
    String content;

    @Column(name = "positive_example", nullable = true)
    String positiveExample;

    @Column(name = "counter_example", nullable = true)
    String counterExample;

    @Column(name = "note")
    String note;

    @Column(name = "created_at")
    Instant createdAt;

    @Column(name = "updated_at")
    Instant updatedAt;

    public void updateFrom(UpdateArticleRequest request){
        if(request.category() != null){
            this.category = request.category();
        }
        if(request.subCategory() != null){
            this.subCategory = request.subCategory();
        }
        if(request.level() != null){
            this.level = request.level();
        }
        if(request.content() != null){
            this.content = request.content();
        }
        if(request.positiveExample() != null){
            this.positiveExample = request.positiveExample();
        }
        if(request.counterExample() != null){
            this.counterExample = request.counterExample();
        }
        if(request.note() != null){
            this.note = request.note();
        }
        this.updatedAt = Instant.now();
    }

    /**
     * 从 请求创建 Article 的方法
     * @param request 创建请求
     * @return article
     */
    public static Article fromRequest(CreateArticleRequest request){
        Article article = new Article();
        article.id = UUID.randomUUID();
        article.category = request.category();
        article.subCategory = request.subCategory();
        article.level = request.level();
        article.content = request.content();
        article.positiveExample = request.positiveExample();
        article.counterExample = request.counterExample();
        article.note = request.note();
        article.createdAt = Instant.now();
        article.updatedAt = Instant.now();
        return article;
    }

    public static Article fromKnowledgeBaseArticle(KnowledgeBaseArticle knowledgeBaseArticle){
        Article article = new Article();
        article.id = UUID.fromString(knowledgeBaseArticle.id());
        article.category = knowledgeBaseArticle.category();
        article.subCategory = knowledgeBaseArticle.subCategory();
        article.level = knowledgeBaseArticle.level();
        article.content = knowledgeBaseArticle.content();
        article.positiveExample = knowledgeBaseArticle.positiveExample();
        article.counterExample = knowledgeBaseArticle.counterExample();
        article.note = knowledgeBaseArticle.note();
        return article;
    }

    public KnowledgeBaseArticle toKnowledgeBaseArticle(){
        return new KnowledgeBaseArticle(
                id.toString(),
                category,
                subCategory,
                level,
                content,
                positiveExample,
                counterExample,
                note,
                createdAt,
                updatedAt
        );
    }
}
