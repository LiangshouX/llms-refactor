package com.liangshou.llmsrefactor.knowlefgebase.request;

import org.jetbrains.annotations.Nullable;

/**
 * @author X-L-S
 */
public record CreateArticleRequest(String category,
                                   String subCategory,
                                   String level,
                                   String content,
                                   @Nullable String positiveExample,
                                   @Nullable String counterExample,
                                   @Nullable String note) {
}
