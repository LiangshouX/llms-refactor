//package com.liangshou.samples;
//
//import com.alibaba.dashscope.aigc.conversation.Conversation;
//import com.alibaba.dashscope.aigc.conversation.ConversationParam;
//import com.alibaba.dashscope.aigc.conversation.ConversationResult;
//import com.alibaba.dashscope.exception.InputRequiredException;
//import com.alibaba.dashscope.exception.NoApiKeyException;
//import com.alibaba.dashscope.utils.Constants;
//import jakarta.annotation.PostConstruct;
//import jakarta.annotation.Resource;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.stereotype.Component;
//import org.springframework.stereotype.Service;
//
///**
// * @author X-L-S
// */
//@Component
//public class QuickStartTongyi {
//    @Value("${dashscope.api-key:not-found}")
//    private String apiKey;
//
//    public void quickStart() throws NoApiKeyException, InputRequiredException {
//        // String apiKey = "sk-4d4059ba0fba43cca6b68a664f5ce214";
//        System.out.println("apiKey = " + apiKey);
//        Conversation conversation = new Conversation();
//        Constants.apiKey = apiKey;
//        String prompt = "用萝卜、土豆、茄子做饭，给我个菜谱。";
//        ConversationParam param = ConversationParam
//                .builder()
//                .model(Conversation.Models.QWEN_TURBO)
//                .prompt(prompt)
//                .build();
//        ConversationResult result = conversation.call(param);
//        // System.out.println(JsonUtils.toJson(result));
//        System.out.println(result.getOutput().getText());
//    }
////
////    public static void main(String[] args) {
////        QuickStartTongyi start = new QuickStartTongyi();
////        try {
////            start.quickStart();
////        } catch (NoApiKeyException | InputRequiredException e) {
////            throw new RuntimeException(e);
////        }
////        System.exit(0);
////    }
//
//}
