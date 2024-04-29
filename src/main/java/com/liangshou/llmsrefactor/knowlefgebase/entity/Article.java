package com.liangshou.llmsrefactor.knowlefgebase.entity;

import com.liangshou.llmsrefactor.knowlefgebase.request.CreateArticleRequest;
import com.liangshou.llmsrefactor.knowlefgebase.request.UpdateArticleRequest;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.time.Instant;
import java.util.UUID;

import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;


/**
 * 外部知识库的实体类。
 *
 * @author X-L-S
 */
@Entity
@Getter
@Setter
@Table(name = "article")
public class Article {

    @Id
    UUID id;

    @Column(name = "category", nullable = false)
    String category;

    @Column(name = "title", nullable = false)
    String title;

    @Column(name = "content", nullable = false)
    String content;

    @Column(name = "created_at")
    Instant createdAt;

    @Column(name = "updated_at")
    Instant updatedAt;

    public void updateFromRequest(UpdateArticleRequest request){
        if(request.category() != null){
            this.category = request.category();
        }
        if(request.title() != null){
            this.title = request.title();
        }

        if(request.content() != null){
            this.content = request.content();
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
        article.title = request.title();
        article.content = request.content();
        article.createdAt = Instant.now();
        article.updatedAt = Instant.now();
        return article;
    }

    public static Article fromKnowledgeBaseArticle(KnowledgeBaseArticle knowledgeBaseArticle){
        Article article = new Article();
        article.id = UUID.fromString(knowledgeBaseArticle.id());
        article.category = knowledgeBaseArticle.category();
        article.title = knowledgeBaseArticle.title();
        article.content = knowledgeBaseArticle.content();
        article.createdAt = Instant.parse(knowledgeBaseArticle.createAt());
        article.updatedAt = Instant.parse(knowledgeBaseArticle.updateAt());
        return article;
    }

    /**
     * 将 Article 实体对象转换为 KnowledgeBaseArticle 对象
     */
    public KnowledgeBaseArticle toKnowledgeBaseArticle(){
        return new KnowledgeBaseArticle(
                id.toString(),
                category,
                title,
                content,
                createdAt.toString(),
                updatedAt.toString()
        );
    }
}
