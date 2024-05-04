package com.liangshou.llmsrefactor.llms.openai.controller;

import com.liangshou.llmsrefactor.knowlefgebase.entity.KnowledgeBaseArticle;
import com.liangshou.llmsrefactor.llms.openai.GptCompletionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.List;
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

    @PostMapping("/gpt/code")
    public String refactorCodeById(Long id){
        return completionService.refactorCompletion(id).get("generation");
    }

    @GetMapping("/tmp/ft")
    public List<KnowledgeBaseArticle> similiritySearch () {
        String category = "Java";
        String report = "Configurable naming conventions for type declarations. This rule reports type declarations which do not match the regex that applies to their specific kind (e.g. enum or interface). Each regex can be configured through properties.\n" +
                "\n" +
                "By default, this rule uses the standard Java naming convention (Pascal case).";

        List<KnowledgeBaseArticle> res = completionService.findReferenceKnowledge(category, report);

        System.out.println(res);

        return res;
    }

    @GetMapping("/gpt/generateStream")
    public Flux<ChatResponse> generateStream(@RequestParam(value = "message", defaultValue = "Hello" )
                                             String message){
        Prompt prompt = new Prompt(new UserMessage(message));
        return null;
    }
}
