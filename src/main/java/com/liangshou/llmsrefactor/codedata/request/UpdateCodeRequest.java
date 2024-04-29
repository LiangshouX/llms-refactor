package com.liangshou.llmsrefactor.codedata.request;

import org.jetbrains.annotations.Nullable;

/**
 * @author X-L-S
 */
public record UpdateCodeRequest(@Nullable String fileName,
                                @Nullable String languageType,
                                @Nullable String originCode,
                                @Nullable String originReport,
                                @Nullable Integer originNumProblem,
                                @Nullable String newCode,
                                @Nullable String newReport,
                                @Nullable Integer newNumProblem,
                                @Nullable Boolean isSame) {
}
