package com.liangshou.llmsrefactor.knowlefgebase;

import com.liangshou.llmsrefactor.knowlefgebase.entity.Article;
import com.liangshou.llmsrefactor.knowlefgebase.entity.Articles;
import com.liangshou.llmsrefactor.knowlefgebase.entity.Categories;
import com.liangshou.llmsrefactor.knowlefgebase.entity.Category;
import com.liangshou.llmsrefactor.knowlefgebase.event.KnowledgeBaseArticleDeletedEvent;
import com.liangshou.llmsrefactor.knowlefgebase.event.KnowledgeBaseArticleUpdatedEvent;
import com.liangshou.llmsrefactor.knowlefgebase.event.KnowledgeBaseArticlesImportedEvent;
import com.liangshou.llmsrefactor.knowlefgebase.request.CreateArticleRequest;
import com.liangshou.llmsrefactor.knowlefgebase.request.UpdateArticleRequest;
import com.liangshou.llmsrefactor.model.entity.KnowledgeBaseArticle;
import jakarta.annotation.Resource;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.StreamSupport;

/**
 * 知识库服务类
 *
 * @author X-L-S
 */
@Service
public class KnowledgeBaseService implements ApplicationEventPublisherAware {
    @Resource
    private final KnowledgeBaseArticleRepository knowledgeBaseArticleRepository;
    @Resource
    private ApplicationEventPublisher applicationEventPublisher;

    public KnowledgeBaseService(
            KnowledgeBaseArticleRepository knowledgeBaseArticleRepository) {
        this.knowledgeBaseArticleRepository = knowledgeBaseArticleRepository;
    }

    @Override
    public void setApplicationEventPublisher(
            @NotNull ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public Article createArticle(CreateArticleRequest request){
        Article article = knowledgeBaseArticleRepository.save(Article.fromRequest(request));
        applicationEventPublisher.publishEvent(article.toKnowledgeBaseArticle());
        return article;
    }

    public Optional<Article> updateArticle(UUID id, UpdateArticleRequest request){

        return knowledgeBaseArticleRepository.findById(id).map(article -> {
            article.updateFrom(request);
            var updated = knowledgeBaseArticleRepository.save(article);
            applicationEventPublisher.publishEvent(
                    new KnowledgeBaseArticleUpdatedEvent(
                            updated.toKnowledgeBaseArticle()
                    )
            );
            return updated;
        });
    }

    public void deleteArticle(UUID id){
        knowledgeBaseArticleRepository.findById(id).ifPresent(article -> {
            knowledgeBaseArticleRepository.delete(article);
            applicationEventPublisher.publishEvent(
                    new KnowledgeBaseArticleDeletedEvent(
                            article.toKnowledgeBaseArticle()
                    )
            );
        });
    }

    public void importArticles(List<KnowledgeBaseArticle> articles) {
        var saved = knowledgeBaseArticleRepository.saveAll(
                articles.stream()
                        .map(Article::fromKnowledgeBaseArticle)
                        .toList());
        applicationEventPublisher.publishEvent(
                new KnowledgeBaseArticlesImportedEvent(
                        StreamSupport.stream(saved.spliterator(), false)
                                .map(Article::toKnowledgeBaseArticle).toList()));
    }

    public Articles findArticles(String category, Pageable pageable) {
        Page<Article> page;
        if (category == null) {
            page = knowledgeBaseArticleRepository.findAll(pageable);
        } else {
            page = knowledgeBaseArticleRepository.findByCategory(category, pageable);
        }
        return null;
    }

    public Categories findAllCategories(){
        return new Categories(
                knowledgeBaseArticleRepository.findAllCategories()
                        .stream()
                        .map(Category::new)
                        .toList()
        );
    }

}
