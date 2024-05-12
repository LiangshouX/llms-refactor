package com.liangshou.llmsrefactor.metrics.jaccard;

import java.util.ArrayList;
import java.util.List;

/**
 * @author X-L-S
 */
public class JaccardDemo {

    public static void main(String[] args) {
        String code1 = """
                	
                    package java_programs.extra;
                    
                    public class BinarySearch {
                    public static int findFirstInSorted(int[] arr, int x) {
                    int lo = 0;
                    int hi = arr.length;
                    
                    while (lo < hi) {
                    int mid = lo + (hi - lo) / 2;
                    
                    if (x == arr[mid] && (mid == 0 || x != arr[mid - 1])) {
                    return mid;
                    } else if (x <= arr[mid]) {
                    hi = mid;
                    } else {
                    lo = mid + 1;
                    }
                    }
                    return -1;
                    }
                    }           \s
                """;
        String code2 = """
                package java_programs.extra;
                                
                import java.util.*;
                                
                public class BINARY_SEARCH {
                    public static int findFirstInSorted(int[] arr, int x) {
                        int lo = 0;
                        int hi = arr.length;
                                
                        while (lo <= hi) {
                            int mid = (lo + hi) / 2;
                                
                            if (x == arr[mid] && (mid == 0 || x != arr[mid - 1])) {
                                return mid;
                            } else if (x <= arr[mid]) {
                                hi = mid;
                            } else {
                                lo = mid + 1;
                            }
                        }
                        return -1;
                    }
                }
                """;
        JaccardSimilarity jaccardSimilarity = new JaccardSimilarity(code1, code2);

        jaccardSimilarity.buildSet();

        System.out.println(jaccardSimilarity.calculateJaccardSimilarity());
    }

    public static void calculateAndPrintSimilarities(List<String> strings) {
        List<Double> similarities = new ArrayList<>();

        // 计算每对字符串的相似度
        for (int i = 0; i < strings.size(); i++) {
            for (int j = i + 1; j < strings.size(); j++) {
                JaccardSimilarity similarityCalculator = new JaccardSimilarity(strings.get(i), strings.get(j));
                similarityCalculator.buildSet();
                double similarity = similarityCalculator.calculateJaccardSimilarity();
                similarities.add(similarity);

                System.out.printf("Similarity between '%s' and '%s': %.2f%n", strings.get(i), strings.get(j), similarity);
            }
        }

        // 计算平均值
        double average = similarities.stream().mapToDouble(Double::doubleValue).average().orElse(0);
        System.out.printf("Average Similarity: %.2f%n", average);

        // 计算方差
        double variance = similarities.stream()
                .mapToDouble(v -> Math.pow(v - average, 2))
                .average()
                .orElse(0);
        System.out.printf("Variance: %.2f%n", variance);
    }
}
