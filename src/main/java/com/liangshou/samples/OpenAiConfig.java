package com.liangshou.samples;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author X-L-S
 */
@ConfigurationProperties(prefix = "spring.ai.openai")
public record OpenAiConfig ( OpenAiChatConfig chat, OpenAiEmbeddingConfig embedding ){

    public record OpenAiChatConfig(String model, float temperature){

    }

    public record OpenAiEmbeddingConfig(String model){

    }
}
