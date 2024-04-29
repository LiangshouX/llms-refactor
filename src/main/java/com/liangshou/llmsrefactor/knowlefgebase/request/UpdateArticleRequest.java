package com.liangshou.llmsrefactor.knowlefgebase.request;

import org.jetbrains.annotations.Nullable;

/**
 * @param category 文档所属于的语言类别
 * @param title 文档标题
 * @param content 文档内容
 * @author X-L-S
 */
public record UpdateArticleRequest(@Nullable String category,
                                   @Nullable String title,
                                   @Nullable String content) {
}
