package com.liangshou.llmsrefactor.knowlefgebase.request;

import org.jetbrains.annotations.Nullable;

/**
 * @author X-L-S
 */
public record CreateArticleRequest(String category,
                                   String subCategory,
                                   String itemId,
                                   String content) {
}
