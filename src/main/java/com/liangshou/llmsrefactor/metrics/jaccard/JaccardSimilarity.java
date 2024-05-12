package com.liangshou.llmsrefactor.metrics.jaccard;

import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

/**
 * 计算 Jaccard 相似度的工具类
 * @author X-L-S
 */
@Getter
public class JaccardSimilarity {

    private final String firstCode;

    private final String secondCode;

    private Set<String> firstCodeSet;

    private Set<String> secondCodeSet;

    public JaccardSimilarity(String firstCode, String secondCode){
        this.firstCode = firstCode;
        this.secondCode = secondCode;
    }

    /**
     * 计算两个集合的 Jaccard 相似度
     * @return Jaccard相似度
     */
    public double calculateJaccardSimilarity() {
        Set<String> intersection = new HashSet<>(this.firstCodeSet);
        intersection.retainAll(this.secondCodeSet); // 交集

        Set<String> union = new HashSet<>(this.firstCodeSet);
        union.addAll(this.secondCodeSet); // 并集

        return (double) intersection.size() / union.size(); // 相似度计算
    }

    /**
     * 把两个代码字段构建为集合
     */
    public void buildSet(){
        this.firstCodeSet = CodeToHashSet.convert(this.firstCode);
        this.secondCodeSet = CodeToHashSet.convert(this.secondCode);
    }


}
