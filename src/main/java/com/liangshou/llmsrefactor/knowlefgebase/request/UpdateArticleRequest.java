package com.liangshou.llmsrefactor.knowlefgebase.request;

import org.jetbrains.annotations.Nullable;

/**
 * @author X-L-S
 */
public record UpdateArticleRequest(@Nullable String category,
                                   @Nullable String subCategory,
                                   @Nullable String itemId,
                                   @Nullable String content) {
}
