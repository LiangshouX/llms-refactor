package com.liangshou.llmsrefactor.codedata.request;

import org.jetbrains.annotations.Nullable;

/**
 * @author X-L-S
 */
public record CreateCodeRequest(String fileName,
                                String languageType,
                                String originCode,
                                @Nullable String originReport,
                                @Nullable Integer originNumProblem,
                                @Nullable String newCode,
                                @Nullable String newReport,
                                @Nullable Integer newNumProblem,
                                @Nullable Boolean isSame) {
}
