package com.liangshou.llmsrefactor.openai;

import org.springframework.ai.openai.OpenAiChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author X-L-S
 */
@Service
public class GptCompletionServiceImpl implements GptCompletionService {

    private final OpenAiChatClient chatClient;

    @Autowired
    public GptCompletionServiceImpl(OpenAiChatClient chatClient){
        this.chatClient = chatClient;
    }
    @Override
    public void buildPrompt() {

    }

    @Override
    public String doCompletion() {
        return null;
    }
}
