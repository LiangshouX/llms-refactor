package com.liangshou.samples;

import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;

/**
 * 与 ChatGPT 对话的聊天客户端
 * @author X-L-S
 */
public class OpenAiChatClient implements ChatClient {
    private final OpenAiConfig config;

    public OpenAiChatClient(OpenAiConfig config){
        this.config = config;
    }
    @Override
    public ChatResponse call(Prompt prompt) {
        return null;
    }
}
