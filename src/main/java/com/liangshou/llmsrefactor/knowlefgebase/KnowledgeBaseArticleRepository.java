package com.liangshou.llmsrefactor.knowlefgebase;

import com.liangshou.llmsrefactor.knowlefgebase.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author X-L-S
 */
public interface KnowledgeBaseArticleRepository extends
        CrudRepository<Article, UUID> {

    Page<Article> findByCategory(String category, Pageable pageable);

    Optional<Article> findByTitle (String title);

    Page<Article> findAll(Pageable pageable);

    @Query("SELECT distinct category from Article ")
    List<String> findAllCategories();
}
