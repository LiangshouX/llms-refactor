package com.liangshou.llmsrefactor.pmd;

import net.sourceforge.pmd.PMDConfiguration;
import net.sourceforge.pmd.reporting.Report;
import net.sourceforge.pmd.reporting.RuleViolation;

import java.util.List;

/**
 * 调用 PMD 执行代码分析的接口
 * @author X-L-S
 */
public interface PMDAnalysisService {
    /**
     * 构建 PMDConfiguration 对象，配置PMD分析器
     * @param languageType 编程语言的类别，用于加载不同的规则集
     * @return PMDConfiguration 对象
     */
    PMDConfiguration createPmdConfig(String languageType);

    /**
     * 执行 PMD 代码分析，根据不同的 Root（重构前的还是重构后的）选择文件
     * @param config PMD 配置对象
     * @param fileRoot 扫描文件的根目录
     * @param fileName 文件名
     * @return PMD Report
     */
    List<RuleViolation> analyzeCode(PMDConfiguration config, String fileRoot, String fileName);
}
