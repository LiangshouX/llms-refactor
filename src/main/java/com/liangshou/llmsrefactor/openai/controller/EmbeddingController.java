package com.liangshou.llmsrefactor.openai.controller;

import org.springframework.ai.embedding.EmbeddingClient;
import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * Embedding 相关的 Controller
 * @author X-L-S
 */
@RestController
public class EmbeddingController {
    private final EmbeddingClient embeddingClient;

    @Autowired
    public EmbeddingController(EmbeddingClient embeddingClient){
        this.embeddingClient = embeddingClient;
    }
    @GetMapping("/ai/embedding")
    public Map embed(@RequestParam(value = "message", defaultValue = "Hello") String message){
        EmbeddingResponse embeddingResponse = this.embeddingClient.embedForResponse(List.of(message));
        return Map.of("embedding", embeddingResponse);
    }
}
