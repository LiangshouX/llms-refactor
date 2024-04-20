package com.liangshou.llmsrefactor.knowlefgebase;

import com.liangshou.llmsrefactor.knowlefgebase.entity.Article;
import com.liangshou.llmsrefactor.knowlefgebase.entity.Articles;
import com.liangshou.llmsrefactor.knowlefgebase.entity.Categories;
import com.liangshou.llmsrefactor.knowlefgebase.request.CreateArticleRequest;
import com.liangshou.llmsrefactor.knowlefgebase.request.UpdateArticleRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

/**
 * @author X-L-S
 */
@RestController
@RequestMapping("/api/v1/kb")
public class KnowledgeBaseController {
    private final KnowledgeBaseService knowledgeBaseService;

    @Autowired
    public KnowledgeBaseController(KnowledgeBaseService knowledgeBaseService){
        this.knowledgeBaseService = knowledgeBaseService;
    }

    @PostMapping("/article")
    public ResponseEntity<Void> createArticle(
            @RequestBody CreateArticleRequest request) {
        var created = knowledgeBaseService.createArticle(request);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/{0}")
                .build(created.getId());
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/article/{id}")
    public ResponseEntity<Article> updateArticle(@PathVariable("id") String id,
                                                 @RequestBody UpdateArticleRequest request) {
        UUID uuid;
        try {
            uuid = UUID.fromString(id);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
        return knowledgeBaseService.updateArticle(uuid, request).map(
                ResponseEntity::ok
        ).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/article/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable("id") String id) {
        UUID uuid;
        try {
            uuid = UUID.fromString(id);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
        knowledgeBaseService.deleteArticle(uuid);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/article")
    public ResponseEntity<Articles> listArticles(
            @RequestParam(value = "category", required = false) String category,
            @RequestParam(value = "current", defaultValue = "1") int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "20") int pageSize) {
        var articles = knowledgeBaseService.findArticles(
                StringUtils.trimToNull(category),
                PageRequest.of(Math.max(pageNumber - 1, 0), pageSize,
                        Sort.by(Sort.Direction.DESC, "updatedAt")));
        return ResponseEntity.ok(articles);
    }


    @GetMapping("/categories")
    public Categories listQuestionCategories() {
        return knowledgeBaseService.findAllCategories();
    }
}
