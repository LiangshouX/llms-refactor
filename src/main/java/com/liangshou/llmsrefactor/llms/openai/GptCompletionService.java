package com.liangshou.llmsrefactor.llms.openai;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.liangshou.llmsrefactor.codedata.CodeDataRepository;
import com.liangshou.llmsrefactor.codedata.CodeDataService;
import com.liangshou.llmsrefactor.codedata.entity.CodeDataEntity;
import com.liangshou.llmsrefactor.document.model.Documents;
import com.liangshou.llmsrefactor.knowlefgebase.KnowledgeBaseArticleRepository;
import com.liangshou.llmsrefactor.knowlefgebase.entity.KnowledgeBaseArticle;
import com.liangshou.llmsrefactor.pmd.PmdAnalysisService;
import com.liangshou.llmsrefactor.codedata.constant.CodeDataPathConstant.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.messages.*;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.openai.OpenAiChatClient;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.filter.FilterExpressionBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.Map;

import static com.liangshou.llmsrefactor.document.model.DocumentFieldNames.CATEGORY;
import static com.liangshou.llmsrefactor.pmd.constant.PmdConfigConstant.JAVA_REFACTORED_PATH;

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

    private final CodeDataRepository codeDataRepository;

    private final PmdAnalysisService pmdAnalysisService;

    private final ResourceLoader resourceLoader;

    private final VectorStore vectorStore;

    private final ObjectMapper objectMapper;

    private final KnowledgeBaseArticleRepository knowledgeBaseArticleRepository;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Value("classpath:/prompts/refactor-code-zero-shot.st")
    private Resource promptCodeRefactor;

    @Autowired
    public GptCompletionService(OpenAiChatClient chatClient,
                                CodeDataRepository codeDataRepository,
                                PmdAnalysisService pmdAnalysisService,
                                ResourceLoader resourceLoader,
                                VectorStore vectorStore,
                                ObjectMapper objectMapper,
                                KnowledgeBaseArticleRepository knowledgeBaseArticleRepository){
        this.chatClient = chatClient;
        this.codeDataRepository = codeDataRepository;
        this.pmdAnalysisService = pmdAnalysisService;
        this.resourceLoader = resourceLoader;
        this.vectorStore = vectorStore;
        this.objectMapper = objectMapper;
        this.knowledgeBaseArticleRepository = knowledgeBaseArticleRepository;
    }


    public void streamRefactorCompletion(){

        // String initialPrompt = "请记住并理解下面的这段信息：" + dynamicPromptTemplate;
        var template = new PromptTemplate(promptCodeRefactor);

        chatClient.stream(template.create());
    }

    /**
     * 通过 ChatGPT 接口发送代码重构指令的方法。所需的一些数据从数据库中加载
     *
     * @return 返回消息的封装
     */
    public Map<String, String> refactorCompletion(Long id){
        var template = new PromptTemplate(promptCodeRefactor);


        CodeDataEntity codeData = codeDataRepository.findById(id).get();

        // var referenceKnowledge = findReferenceKnowledge("Java", codeData.getOriginReport());

        var prompt = template.create(Map.of(
                // "pmdReport", codeData.getOriginReport(),
                // "referenceKnowledge", " ",
                "code", codeData.getOriginCode()
        ));
        // System.out.println(prompt.toString());

        var response = chatClient.call(prompt);

        String output = response.getResult().getOutput().getContent();

        // 去除前后缀
        output = output.replaceFirst("```java", "").replaceFirst("```$", "");
        codeData.setNewCode(output);
        codeData.setUpdateAt(Instant.now());

        codeDataRepository.save(codeData);
        logger.info("Saved {} to database, start saving to file...", id);

        saveNewCodeToFile(output, JAVA_REFACTORED_PATH, codeData.getFileName());

        return Map.of("generation", output);
    }

    /**
     * TODO： 从存入向量数据库的外部的知识库中执行相似性搜索，找到参考信息
     *
     * @return 暂时 null
     */
    public List<KnowledgeBaseArticle> findReferenceKnowledge(String category, String report){

        System.out.println(vectorStore.toString());
        return
        vectorStore.similaritySearch(
                SearchRequest.query(report)
                        .withTopK(1)
                        .withFilterExpression(
                                new FilterExpressionBuilder().
                                        eq(CATEGORY, category)
                                        .build()
                        )
        ).stream()
                .map(Documents::fromDocument)
                .toList();
    }

    /**
     * 辅助方法，将生成的新代码 去除首位标记 后存入重构路径
     *
     * @param codeContent 代码内容
     * @param filePath 存放的路径
     * @param fileName 保存的文件名
     */
    private void saveNewCodeToFile (String codeContent, String filePath, String fileName) {

        // 拼接完整的文件路径
        String completePath = filePath + fileName;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(completePath))) {
            // 将处理后的代码写入文件
            writer.write(codeContent);
            logger.info("File {} saved successfully..", fileName);
        } catch (IOException e) {
            logger.error("Error occurred when saving file {}..", fileName);
            // throw new RuntimeException(e);
        }
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

    /**
     * 对话测试方法，仅用于测试是否能与 ChatGPT 正常对话
     *
     * @param msg 消息
     */
    @Deprecated
    public String completionDemo(String msg){
        var value = chatClient.call(msg);
        return  value;
    }

}
