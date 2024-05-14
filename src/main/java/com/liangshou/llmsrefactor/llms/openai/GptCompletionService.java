package com.liangshou.llmsrefactor.llms.openai;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.util.concurrent.RateLimiter;
import com.liangshou.llmsrefactor.codedata.entity.CodeCompareEntity;
import com.liangshou.llmsrefactor.codedata.entity.CodeDataEntity;
import com.liangshou.llmsrefactor.codedata.repository.CodeCompareRepository;
import com.liangshou.llmsrefactor.codedata.repository.CodeDataRepository;
import com.liangshou.llmsrefactor.document.model.Documents;
import com.liangshou.llmsrefactor.knowlefgebase.KnowledgeBaseArticleRepository;
import com.liangshou.llmsrefactor.knowlefgebase.entity.KnowledgeBaseArticle;
import com.liangshou.llmsrefactor.llms.openai.entity.CodeCompareResult;
import com.liangshou.llmsrefactor.metrics.pmd.entity.PmdViolation;
import com.liangshou.llmsrefactor.metrics.pmd.entity.PmdViolations;
import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.liangshou.llmsrefactor.document.model.DocumentFieldNames.CATEGORY;
import static com.liangshou.llmsrefactor.metrics.pmd.constant.PmdConfigConstant.HTML_CODE_ROOT;
import static com.liangshou.llmsrefactor.metrics.pmd.constant.PmdConfigConstant.JAVA_REFACTORED_PATH;

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

    private final CodeCompareRepository codeCompareRepository;

    private final VectorStore vectorStore;

    private final ObjectMapper objectMapper;

    private final KnowledgeBaseArticleRepository knowledgeBaseArticleRepository;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final RateLimiter rateLimiter = RateLimiter.create(1);

    private final  int STEP = 5;

    @Value("classpath:/prompts/refactor-code-level-2.st")
    private Resource promptCodeRefactor;

    @Autowired
    public GptCompletionService(OpenAiChatClient chatClient,
                                CodeDataRepository codeDataRepository,
                                CodeCompareRepository codeCompareRepository,
                                VectorStore vectorStore,
                                ObjectMapper objectMapper,
                                KnowledgeBaseArticleRepository knowledgeBaseArticleRepository){
        this.chatClient = chatClient;
        this.codeDataRepository = codeDataRepository;
        this.codeCompareRepository = codeCompareRepository;
        this.vectorStore = vectorStore;
        this.objectMapper = objectMapper;
        this.knowledgeBaseArticleRepository = knowledgeBaseArticleRepository;
    }


    /**
     * 通过 ChatGPT 接口发送代码重构指令的方法。所需的一些数据从数据库中加载
     *
     * @return 返回消息的封装
     */
    public Map<String, String> refactorCompletion(Long id){
        logger.info("Using prompt {} to refactor", promptCodeRefactor.getFilename());


        CodeDataEntity codeData = codeDataRepository.findById(id).get();

        String output;

        if (promptCodeRefactor.getFilename() != null &&
                promptCodeRefactor.getFilename().equals("refactor-code-level-1.st")) {

            var template = new PromptTemplate(promptCodeRefactor);
            var prompt = createPrompt(template, codeData.getOriginCode(), null);

            var response = chatClient.call(prompt);
            output = response.getResult().getOutput().getContent();
            // output = streamCompletion(prompt);
        }

        // 针对后三个 Level 的重构，直接调用多轮对话
        else if (Objects.equals(promptCodeRefactor.getFilename(), "refactor-code-level-2.st" )
                || Objects.equals(promptCodeRefactor.getFilename(), "refactor-code-level-3.st")
                || Objects.equals(promptCodeRefactor.getFilename(), "refactor-code-level-4.st")) {

            output = multiRefactorCompletion(id);
        }

        else {
            logger.error("prompt resource {} is not usable for Refactoring", promptCodeRefactor.getFilename());
            throw new RuntimeException("Your code is joking to you.....");
        }


        // 去除前后缀
        output = removeCodeFences(output);

        codeData.setNewCode(output);
        codeData.setUpdateAt(Instant.now());

        codeDataRepository.save(codeData);
        logger.info("Saved {} to database, start saving to file...", id);

        String refactorFilePath;
        if(codeData.getLanguageType().equals("java")){
            refactorFilePath = JAVA_REFACTORED_PATH;
        } else if (codeData.getLanguageType().equals("html")) {
            refactorFilePath = HTML_CODE_ROOT;
        }else {
            logger.error("Language Type {} is not supported.", codeData.getLanguageType());
            refactorFilePath = JAVA_REFACTORED_PATH;
        }
        saveNewCodeToFile(output, refactorFilePath, codeData.getFileName());

        return Map.of("generation", output);
    }

    /**
     *  多轮重构对话
     */
    public String multiRefactorCompletion(Long id) {
        var template = new PromptTemplate(promptCodeRefactor);

        CodeDataEntity codeData = codeDataRepository.findById(id).get();



        if (Objects.equals(promptCodeRefactor.getFilename(), "refactor-code-level-2.st")) {
            String originReport = codeData.getOriginReport();
            String stageCode = codeData.getOriginCode();

            List<String> originReportList = splitReportIntoSubJson(originReport, STEP);
            List<Message> messages = new ArrayList<>();

            for (String subReport : originReportList) {
                var prompt = createPrompt(template, stageCode, subReport);
                // messages.add(new SystemMessage(prompt.getContents()));

                // if (messages.size() >= originReportList.size() || messages.size() >= 3) {
                //   messages.remove(0);
                // }

                stageCode = removeCodeFences(streamCompletion(prompt));
            }

            return stageCode;
        }
        else if (Objects.equals(promptCodeRefactor.getFilename(), "refactor-code-level-3.st")) {

            List<List<String>> refKnowledge = queryPmdRules(codeData);
            String stageCode = codeData.getOriginCode();

            for (List<String> ref: refKnowledge){
                var prompt = createPrompt(template, stageCode, ref.toString());
                stageCode = removeCodeFences(streamCompletion(prompt));
            }

            return stageCode;
        }
        else if (Objects.equals(promptCodeRefactor.getFilename(), "refactor-code-level-4.st")) {

            Message msg = new SystemMessage("你是一位经验丰富的软件工程师，请分点列出下面代码中需要重构的重构点。\n" +
                    "仅生成重构点，无需重构:\n" + codeData.getOriginCode());

            try {
                String refKnowledge = chatClient.call(new Prompt(msg)).getResult().getOutput().getContent();

                logger.info("Reference Knowledge generated for LEVEL-4...");

                String stageCode = codeData.getOriginCode();

                var prompt = createPrompt(template, stageCode, refKnowledge);

                return streamCompletion(prompt);
            }
            catch (Exception e) {
                logger.error("生成重构点时出现错误！", e);
                return null;
            }
        }

        else {
            logger.error("Prompt File is not correct (must be refactor-code-level-1.st to refactor-code-level-4.st). " +
                    "Check that");

            return null;
        }

    }

    /**
     * 用于对比重构前后代码功能是否一致的对话
     */
    public void compareTwoCodeCompletion (Long id) {
        if (!Objects.equals(promptCodeRefactor.getFilename(), "refactor-code-level-5.st")) {
            logger.warn("for compareTwoCodeCompletion, you should use prompt level-5");
            return ;
        }
        CodeDataEntity codeData = codeDataRepository.findById(id).get();

        String originCode = codeData.getOriginCode();
        String newCode = codeData.getNewCode();

        String firstPromptString = """
                请阅读并理解这段代码，如果你已经理解并记住它，请回复“OK”：
                %s
                """.formatted(originCode);
        String secondPromptString = createPrompt(new PromptTemplate(promptCodeRefactor),
                newCode, null).getContents();

        List<Message> messages = new ArrayList<>();

        messages.add(new SystemMessage(firstPromptString));

        String firstResponse = streamCompletion(new Prompt(messages));
//        System.out.println("AI Response1 :\n" + firstResponse);

        messages.add(new SystemMessage(secondPromptString));
        String secondResponse = streamCompletion(new Prompt(messages));
        secondResponse = secondResponse.replaceFirst("```json", "")
                                        .replaceFirst("```", "");
//        System.out.println("AI Response2 :\n" + secondResponse);

        try {
            CodeCompareResult codeCompare = objectMapper.readValue(secondResponse, CodeCompareResult.class);
            codeData.setIsSame(codeCompare.maintain().equals("yes"));
            codeData.setDescription(codeCompare.description());
            codeData.setUpdateAt(Instant.now());
            codeDataRepository.save(codeData);

            logger.info("Compare Complete with code id {}", id);
        } catch (Exception e) {
            logger.error("Failed to Convert AI Response to CondeCompareEntity, retry please");
            System.out.println(secondResponse);
        }
    }


    /**
     * 针对 流式接口调用方法
     * @param prompt Prompt对象
     * @return Output
     */
    public String streamCompletion (Prompt prompt) {
        StringBuilder outputBuilder = new StringBuilder();

        chatClient.stream(prompt)
                .doOnNext(chatResponse -> {
                    synchronized (outputBuilder) {
                        var result = chatResponse.getResult();

                        if (result != null) {
                            var output = result.getOutput();

                            if (output != null) {
                                String content = output.getContent();

                                if (content != null) {
                                    // System.out.println(content);
                                    // logger.info("Refactor is running at {}", Instant.now());
                                    outputBuilder.append(content);
                                }
                                else {
                                    logger.warn("Content is null in ChatOutput.");
                                }
                            }
                            else {
                                logger.warn("Output is null in ChatResult.");
                            }
                        }
                        else {
                            logger.warn("Result is null in ChatResponse.");
                        }

                        // String content = chatResponse.getResult().getOutput().getContent();
                        //outputBuilder.append(content);
                    }
                })
                .doOnComplete(() -> {
                    // 流完成后的处理逻辑，此时outputBuilder已经包含了所有内容
                    // System.out.println("All responses processed. OutputBuilder content: " + outputBuilder.toString());
                    // 这里可以继续进行你需要的操作
                    logger.info("All responses processed. OutputBuilder content length: {}", outputBuilder.length());
                })
                .doOnError(error -> {
                    logger.error("An error occurred: {}", error.getMessage());
                }).blockLast();
                //.subscribe();
//        chatClient.stream(prompt)
//                .subscribe(
//                        // 处理每个 chatResponse 对象
//                        chatResponse -> {
//                            synchronized (outputBuilder) {
//                                String content = chatResponse.getResult().getOutput().getContent();
//
//                                // System.out.println("Received ChatResponse:\t" + content);
//                                outputBuilder.append(content);
//                                System.out.println(outputBuilder.toString());
//                                tmp.add(content);
//                            }
//                        },
//                        // 发生错误时的处理
//                        error -> {
//                            logger.error("An error occurred in refactorCompletionStream method: {}", error.getMessage());
//                        },
//                        () -> {
//                            // 流完成时调用
//                            logger.info("Streaming completed.");
//                        }
//                );


        // System.out.println("streamCompletion: \n" + outputBuilder.toString());
        logger.info("Stream Completion at {}", Instant.now());
        return outputBuilder.toString();
    }

    /**
     * 先利用 LEVEL-1 的重构指令对同一段代码进行重复实验，比较重构的稳定性
     * @param id 代码的数据库 id
     */
    public void codeRefactorCompare (Long id) {
        if (!Objects.equals(promptCodeRefactor.getFilename(), "refactor-code-level-1.st")){
            logger.error("Code Refactor Only User refactor-code-level-1.st");
            return;
        };
        logger.info("Using Prompt File {}...", promptCodeRefactor.getFilename());

        var template = new PromptTemplate(promptCodeRefactor);

        CodeCompareEntity codeCompare = codeCompareRepository.findById(id).get();

        var prompt = template.create(Map.of(
                "properties", "可读性，可理解性，设计模式",
                "code", codeCompare.getOriginCode()
        ));

        for (int i = 1; i <= 10; i++){

            rateLimiter.acquire();

            switch (i) {
                case 1 -> {
                    if (codeCompare.getNewCode1() == null){
                        var response = chatClient.call(prompt);
                        String output = response.getResult().getOutput().getContent();

                        output = output.replaceFirst("```java", "")
                                .replaceFirst("```$", "")
                                .replaceFirst("```code", "");

                        codeCompare.setNewCode1(output);
                    }
                }
                case 2 -> {
                    if (codeCompare.getNewCode2() == null){
                        var response = chatClient.call(prompt);
                        String output = response.getResult().getOutput().getContent();
                        output = output.replaceFirst("```java", "")
                                .replaceFirst("```$", "")
                                .replaceFirst("```code", "");

                        codeCompare.setNewCode2(output);
                    }
                }
                case 3 -> {
                    if (codeCompare.getNewCode3() == null){
                        var response = chatClient.call(prompt);
                        String output = response.getResult().getOutput().getContent();
                        output = output.replaceFirst("```java", "")
                                .replaceFirst("```$", "")
                                .replaceFirst("```code", "");
                        codeCompare.setNewCode3(output);
                    }
                }
                case 4 -> {
                    if (codeCompare.getNewCode4() == null){
                        var response = chatClient.call(prompt);
                        String output = response.getResult().getOutput().getContent();
                        output = output.replaceFirst("```java", "")
                                .replaceFirst("```$", "")
                                .replaceFirst("```code", "");

                        codeCompare.setNewCode4(output);
                    }
                }
                case 5 -> {
                    if (codeCompare.getNewCode5() == null){
                        var response = chatClient.call(prompt);
                        String output = response.getResult().getOutput().getContent();
                        output = output.replaceFirst("```java", "")
                                .replaceFirst("```$", "")
                                .replaceFirst("```code", "");

                        codeCompare.setNewCode5(output);
                    }
                }
                case 6 -> {
                    if (codeCompare.getNewCode6() == null){
                        var response = chatClient.call(prompt);
                        String output = response.getResult().getOutput().getContent();
                        output = output.replaceFirst("```java", "")
                                .replaceFirst("```$", "")
                                .replaceFirst("```code", "");

                        codeCompare.setNewCode6(output);
                    }
                }
                case 7 -> {
                    if (codeCompare.getNewCode7() == null){
                        var response = chatClient.call(prompt);
                        String output = response.getResult().getOutput().getContent();
                        output = output.replaceFirst("```java", "")
                                .replaceFirst("```$", "")
                                .replaceFirst("```code", "");

                        codeCompare.setNewCode7(output);
                    }
                }
                case 8 -> {
                    if (codeCompare.getNewCode8() == null){
                        var response = chatClient.call(prompt);
                        String output = response.getResult().getOutput().getContent();
                        output = output.replaceFirst("```java", "")
                                .replaceFirst("```$", "")
                                .replaceFirst("```code", "");
                        codeCompare.setNewCode8(output);
                    }
                }
                case 9 -> {
                    if (codeCompare.getNewCode9() == null){
                        var response = chatClient.call(prompt);
                        String output = response.getResult().getOutput().getContent();
                        output = output.replaceFirst("```java", "")
                                .replaceFirst("```$", "")
                                .replaceFirst("```code", "");

                        codeCompare.setNewCode9(output);
                    }
                }
                case 10 -> {
                    if (codeCompare.getNewCode10() == null){
                        var response = chatClient.call(prompt);
                        String output = response.getResult().getOutput().getContent();
                        output = output.replaceFirst("```java", "")
                                .replaceFirst("```$", "")
                                .replaceFirst("```code", "");
                        codeCompare.setNewCode10(output);
                    }
                }
            }

            codeCompareRepository.save(codeCompare);

        }

    }

    /**
     * 根据选择的不同的 Prompt 模版构建相应的 Prompt
     * @return Prompt对象
     */
    public Prompt createPrompt(PromptTemplate template,
                               String codeData,
                               @Nullable String refKnowledge){

        String promptFile = promptCodeRefactor.getFilename();
        assert promptFile != null;

        switch (promptFile) {
            case "refactor-code-level-1.st" -> {
                return template.create(Map.of(
                        "properties", "可读性，可理解性，设计模式",
                        "code", codeData
                ));
            }
            case "refactor-code-level-2.st" -> {
                if (refKnowledge == null) {
                    logger.warn("RefKnowledge is NULL in LEVEL-2 refactor....");
                    refKnowledge = "";
                }
                return template.create(Map.of(
                        "code", codeData,
                        "pmdReport", refKnowledge
                ));
            }
            case "refactor-code-level-3.st", "refactor-code-level-4.st" -> {

                if (refKnowledge == null) {
                    logger.warn("RefKnowledge is NULL in {} refactor....", promptFile);
                    refKnowledge = "";
                }

                return template.create(Map.of(
                        "code", codeData,
                        "refKnowledge", refKnowledge
                ));
            }
            case "refactor-code-level-5.st" -> {
                return template.create(Map.of(
                        "newCode", codeData
                ));
            }
            default -> {
                logger.error("Prompt File {} is Not correct...", promptFile);
                return null;
            }
        }

    }

    /**
     * 从数据库中查询到对应规则的描述信息
     *
     * @param codeData 代码表中的代码数据实体
     */
    public List<List<String>> queryPmdRules(CodeDataEntity codeData){
        String pmdReport = codeData.getOriginReport();
        try {
            PmdViolations violations = objectMapper.readValue(pmdReport, PmdViolations.class);
            List<PmdViolation> violationList = violations.violations();
            List<String> rules = new ArrayList<>();
            int index = 1;
            for (PmdViolation violation: violationList){
                String title = violation.rule();    // 对应的标题（rule）
                String content = knowledgeBaseArticleRepository.findByTitle(title).get().getContent();
                String singleContent = index + ". " + title + ":" + content;
                rules.add(singleContent);
                index++;
            }

            // 切分
            return new ArrayList<>(rules.stream()
                    .collect(Collectors.groupingBy(it -> (it.hashCode() / STEP) % STEP))
                    .values());

        } catch (Exception e){
            logger.error("Error when convert json String to PmdViolations object");
            return null;
        }

    }

    /**
     * TODO： 从存入向量数据库的外部的知识库中执行相似性搜索，找到参考信息
     *
     * @return 知识列表
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

    /**
     * 把过长的检测报告切分成子串列表，每个子串是完整的JSON格式数据
     *
     * @param jsonString 原始子串
     * @param subSize 切割步长
     */
    public List<String> splitReportIntoSubJson (String jsonString, int subSize) {
        JSONObject json = new JSONObject(jsonString);
        JSONArray violations = json.getJSONArray("violations");
        List<String> result = new ArrayList<>();

        // 计算需要多少个子数组
        int totalViolations = violations.length();
        int numSubArrays = (int) Math.ceil((double) totalViolations / subSize);

        for (int i = 0; i < numSubArrays; i++) {
            int startIndex = i * subSize;
            int endIndex = Math.min(startIndex + subSize, totalViolations);

            // 分割当前子数组
            JSONArray subViolations = new JSONArray();
            for (int j = startIndex; j < endIndex; j++) {
                subViolations.put(violations.get(j));
            }

            // 创建包含子数组的新JSON对象
            JSONObject subJsonObject = new JSONObject();
            subJsonObject.put("fileName", json.getString("fileName"));
            subJsonObject.put("violations", subViolations);
            result.add(subJsonObject.toString());
        }


        return result;
    }

    public String removeCodeFences (String input) {
        // 正则表达式匹配开头的 ```java 或 `code`，包括前后空白
        Pattern startPattern = Pattern.compile("^\\s*(```java|```code)\\s*",
                Pattern.MULTILINE | Pattern.CASE_INSENSITIVE);

        // 使用循环移除开头的标记，直到不再匹配
        Matcher matcher = startPattern.matcher(input);
        while (matcher.find()) {
            input = matcher.replaceFirst("");
            matcher.reset(input); // 重置匹配器以检查更新后的输入字符串
        }

        // 正则表达式匹配末尾的 ```
        Pattern endPattern = Pattern.compile("\\s*```\\s*$",
                Pattern.MULTILINE | Pattern.CASE_INSENSITIVE);

        // 使用循环移除末尾的标记，直到不再匹配
        matcher = endPattern.matcher(input);
        while (matcher.find()) {
            input = matcher.replaceFirst("");
            matcher.reset(input); // 重置匹配器以检查更新后的输入字符串
        }

        return input;
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
    public String completionDemo(String msg){
        var value = chatClient.call(msg);
        logger.info("Execute simple completion method...");
        return  value;
    }

}
