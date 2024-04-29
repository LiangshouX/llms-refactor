package com.liangshou.llmsrefactor.llms.openai.constant;

/**
 * 存储构建 OpenAI 模型的常量参数
 *
 * @author X-L-S
 */
@Deprecated
public interface OpenAiOptionConstant {
    /**
     * 模型选项，传入 withModel 方法
     */
    String DEFAULT_MODEL = "gpt-3.5-turbo";
    String GPT_4_32 = "gpt-4-32k";

    /**
     * 默认的 temperature 值
     */
    Float DEFAULT_TEMPERATURE = 0.4F;

    /**
     * 默认的最大 Token 数
     */
    Integer DEFAULT_MAX_TOKENS = 1024;

    /**
     * 默认的 频率惩罚，
     *  一个 在-2.0和2.0之间的数字。正值根据文本中已有标记的频率对新标记进行惩罚，降低模型完全重复相同行的可能性。
     */
    Float DEFAULT_FREQUENCY_PENALTY = 0.5f;
}
