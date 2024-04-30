package com.liangshou.llmsrefactor.llms.openai.controller;

import com.liangshou.llmsrefactor.llms.openai.GptCompletionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.Map;

/**
 * @author X-L-S
 */
@RestController
@RequestMapping("/api")
@Slf4j
public class GptChatController {

    private final GptCompletionService completionService;

    @Autowired
    public GptChatController(GptCompletionService completionService){
        this.completionService = completionService;
    }

    @GetMapping("/gpt/generate")
    public Map<String, String> completion(@RequestParam(value = "message", defaultValue = "Hello!")
                                          String message){
        var value = completionService.completionDemo(message);
        System.out.println("AI RESPONSE：\n\t" + value);
        return Map.of("generation", value);
    }

    @GetMapping("/gpt/generateStream")
    public Flux<ChatResponse> generateStream(@RequestParam(value = "message", defaultValue = "Hello" )
                                             String message){
        Prompt prompt = new Prompt(new UserMessage(message));
        return null;
    }
}
