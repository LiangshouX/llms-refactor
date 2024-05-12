package com.liangshou.llmsrefactor.metrics.pmd.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * 所需的检测结果的表示类
 *
 * @author X-L-S
 */

@Getter
@Setter
public class PmdAnalysisResult {

    String resultJson;

    int total;
}
