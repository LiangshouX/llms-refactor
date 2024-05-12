package com.liangshou.llmsrefactor.metrics.jaccard;

import com.liangshou.llmsrefactor.codedata.entity.CodeCompareEntity;
import com.liangshou.llmsrefactor.codedata.repository.CodeCompareRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 与 Jaccard 测试服务相关的类。仅限于第一阶段使用
 * @author X-L-S
 */
@Service
public class JaccardService {

    private final CodeCompareRepository codeCompareRepository;

    private final DecimalFormat decimalFormat = new DecimalFormat("#.####");

    public JaccardService(CodeCompareRepository codeCompareRepository) {
        this.codeCompareRepository = codeCompareRepository;
    }

    public void showSummarySimilarities () {
        double[] averages = new double[48];
        double[] variances = new double[48];

        for (long id = 1L; id <= 48; id++){
            CodeCompareEntity codeCompare = codeCompareRepository.findById(id).get();

            List<String> newCodeList = codeCompare.getNewCodeList();

            System.out.printf("=============================== ID = %d ============================%n", id);
            double[] res = calculateAndPrintSimilarities(newCodeList);
            System.out.println("===================================================================");

            int i = Math.toIntExact(id);
            averages[i - 1] = Double.parseDouble(decimalFormat.format(res[0]));
            variances[i - 1] = Double.parseDouble(decimalFormat.format(res[1]));
        }

        System.out.println("average:\t" + Arrays.toString(averages));
        System.out.println("variance:\t" + Arrays.toString(variances));
    }

    public double[] calculateAndPrintSimilarities(List<String> strings) {

        List<Double> similarities = new ArrayList<>();

        // 计算每对字符串的相似度
        for (int i = 0; i < strings.size(); i++) {
            for (int j = i + 1; j < strings.size(); j++) {
                JaccardSimilarity similarityCalculator = new JaccardSimilarity(strings.get(i), strings.get(j));
                similarityCalculator.buildSet();
                double similarity = similarityCalculator.calculateJaccardSimilarity();
                similarities.add(similarity);

                if (j == strings.size() -1 && i == strings.size() - 2){
                    System.out.printf(" %.2f",  similarity);
                }
                else {
                    System.out.printf(" %.2f,",  similarity);
                }
            }
        }

        // 计算平均值
        double average = similarities.stream().mapToDouble(Double::doubleValue).average().orElse(0);
        System.out.printf("\nAverage Similarity: %.2f%n", average);

        // 计算方差
        double variance = similarities.stream()
                .mapToDouble(v -> Math.pow(v - average, 2))
                .average()
                .orElse(0);
        System.out.printf("Variance: %.2f%n", variance);

        return new double[]{average, variance};
    }
}
