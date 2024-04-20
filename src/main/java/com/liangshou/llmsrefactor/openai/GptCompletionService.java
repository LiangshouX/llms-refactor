package com.liangshou.llmsrefactor.openai;

import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.prompt.Prompt;

/**
 * 提供与 ChatGPT 对话的服务接口
 *  提供的功能：
 *      1. 构建 Prompt，包括添加 System Message、User Message
 *      2. 建立与 ChatGPT 的对话
 *      3. 解析响应，将响应存入数据库
 * @author X-L-S
 */
public interface GptCompletionService {
    /**
     * 构建 Prompt，返回的是一个
     */
    public void buildPrompt();

    public String doCompletion();

}
