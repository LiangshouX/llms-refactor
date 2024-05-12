package com.liangshou.llmsrefactor.llms.openai;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author X-L-S
 */
@SpringBootTest
class GptCompletionServiceTest {

    @Resource
    GptCompletionService gptCompletionService;

    @Test
    void removeCodeFencesTest () {
        String input = """
                
                ```code
                
                
                ```java
                package java_programs.extra;
                                
                import java.util.*;
                                
                public class BinarySearch {
                    public static int findFirstElementPositionInSortedArray(final int[] sortedArray, final int target) {
                        int first = 0;
                        int last = sortedArray.length;
                                
                        while (first <= last) {
                            int middle = (first + last) / 2;
                                
                            if (target == sortedArray[middle] && (middle == 0 || target != sortedArray[middle - 1])) {
                                return middle;
                            } else if (target <= sortedArray[middle]) {
                                last = middle;
                            } else {
                                first = middle + 1;
                            }
                        }
                        return -1;
                    }
                }
                ```
                ```
                """;
        System.out.println(gptCompletionService.removeCodeFences(input));
    }

    @Test
    void splitReportIntoSubJson() {
        String report = """
                {
                    "fileName":"MERGESORT.java",
                    "violations":[
                        {
                         "description":"This utility class has a non-private constructor",
                         "rule":"UseUtilityClass",
                         "ruleSet":"Design",
                         "priority":3
                        },
                        {
                         "description":"Avoid using implementation types like 'ArrayList'; use the interface instead",
                         "rule":"LooseCoupling","ruleSet":
                         "Best Practices",
                         "priority":3
                        },
                        {
                         "description":"Avoid using implementation types like 'ArrayList'; use the interface instead",
                          "rule":"LooseCoupling",
                          "ruleSet":"Best Practices",
                          "priority":3
                         },
                         {
                          "description":"Parameter 'left' is not assigned and could be declared final",
                          "rule":"MethodArgumentCouldBeFinal",
                          "ruleSet":"Code Style",
                          "priority":3
                          },
                          {
                           "description":"Avoid using implementation types like 'ArrayList'; use the interface instead",
                           "rule":"LooseCoupling",
                           "ruleSet":"Best Practices",
                           "priority":3
                           },{"description":"Parameter 'right' is not assigned and could be declared final","rule":"MethodArgumentCouldBeFinal","ruleSet":"Code Style","priority":3},{"description":"Avoid using implementation types like 'ArrayList'; use the interface instead","rule":"LooseCoupling","ruleSet":"Best Practices","priority":3},{"description":"Local variable 'result' could be declared final","rule":"LocalVariableCouldBeFinal","ruleSet":"Code Style","priority":3},{"description":"Explicit type arguments can be replaced by a diamond: `new ArrayList<>(100)`","rule":"UseDiamondOperator","ruleSet":"Code Style","priority":3},{"description":"Avoid variables with short names like i","rule":"ShortVariable","ruleSet":"Code Style","priority":3},{"description":"Avoid variables with short names like j","rule":"ShortVariable","ruleSet":"Code Style","priority":3},{"description":"Avoid using implementation types like 'ArrayList'; use the interface instead","rule":"LooseCoupling","ruleSet":"Best Practices","priority":3},{"description":"Avoid using implementation types like 'ArrayList'; use the interface instead","rule":"LooseCoupling","ruleSet":"Best Practices","priority":3},{"description":"Parameter 'arr' is not assigned and could be declared final","rule":"MethodArgumentCouldBeFinal","ruleSet":"Code Style","priority":3},{"description":"Substitute calls to size() == 0 (or size() != 0, size() > 0, size() < 1) with calls to isEmpty()","rule":"UseCollectionIsEmpty","ruleSet":"Best Practices","priority":3},{"description":"A method should have only one exit point, and that should be the last statement in the method","rule":"OnlyOneReturn","ruleSet":"Code Style","priority":3},{"description":"Local variable 'middle' could be declared final","rule":"LocalVariableCouldBeFinal","ruleSet":"Code Style","priority":3},{"description":"Avoid using implementation types like 'ArrayList'; use the interface instead","rule":"LooseCoupling","ruleSet":"Best Practices","priority":3},{"description":"Explicit type arguments can be replaced by a diamond: `new ArrayList<>(100)`","rule":"UseDiamondOperator","ruleSet":"Code Style","priority":3},{"description":"Avoid using implementation types like 'ArrayList'; use the interface instead","rule":"LooseCoupling","ruleSet":"Best Practices","priority":3},{"description":"Explicit type arguments can be replaced by a diamond: `new ArrayList<>(100)`","rule":"UseDiamondOperator","ruleSet":"Code Style","priority":3}],"total":21}
                """;

        int subSize = 10;

        List<String> newReports = gptCompletionService.splitReportIntoSubJson(report, subSize);

        for (String newReport : newReports) {
            System.out.println(newReport);
        }
    }
}