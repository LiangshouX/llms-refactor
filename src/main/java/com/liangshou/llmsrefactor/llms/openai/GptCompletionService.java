package com.liangshou.llmsrefactor.llms.openai;

import com.liangshou.llmsrefactor.knowlefgebase.entity.KnowledgeBaseArticle;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.messages.*;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.openai.OpenAiChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 提供与 ChatGPT 对话的服务
 *  提供的功能：
 *      1. 构建 Prompt，包括添加 System Message、User Message
 *      2. 建立与 ChatGPT 的对话
 *      3. 解析响应，将响应存入数据库
 * @author X-L-S
 */
@Service
public class GptCompletionService {

    private final OpenAiChatClient chatClient;

    @Value("classpath:/prompts/refactor-code.st")
    private Resource promptCodeRefactor;

    @Autowired
    public GptCompletionService(OpenAiChatClient chatClient){
        this.chatClient = chatClient;
    }

    /**
     * TODO：通过 ChatGPT 接口发送代码重构指令的方法。所需的一些数据从数据库中加载
     *
     * @return 返回消息的封装
     */
    public Map<String, ChatResponse> refactorCompletion(){
        var template = new PromptTemplate(promptCodeRefactor);
        var referenceKnowledge = findReferenceKnowledge();

        var prompt = template.create(Map.of(
                "pmdReport", null,
                "referenceKnowledge",referenceKnowledge,
                "code", null
        ));

        var response = chatClient.call(prompt);

        String output = response.getResult().getOutput().getContent();

        return Map.of("generation", response);
    }

    /**
     * TODO： 从存入向量数据库的外部的知识库中执行相似性搜索，找到参考信息
     *
     * @return 暂时 null
     */
    public List<KnowledgeBaseArticle> findReferenceKnowledge(){
        return null;
    }

    public List<AbstractMessage> convertMessage(Prompt prompt){
        return prompt.getInstructions().stream().map(
                message -> switch (message.getMessageType()){
                    case SYSTEM -> new SystemMessage("system");
                    case ASSISTANT -> new AssistantMessage("assistant");
                    default -> new UserMessage("");
                }
        ).toList();
    }

}
