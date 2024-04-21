package com.liangshou.llmsrefactor.document.importer;

import com.liangshou.llmsrefactor.document.importer.exception.DocumentImportException;
import com.liangshou.llmsrefactor.knowlefgebase.entity.KnowledgeBaseArticle;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.nio.file.Path;
import java.util.List;
import java.util.Set;

/**
 * 文档导入的业务接口
 *
 * @author X-L-S
 */
public interface DocumentImporter {

    List<KnowledgeBaseArticle> importDocuments(Path path)
        throws DocumentImportException;

    Set<String> supportFileExtensions();
    default boolean support(Path path){
        if(CollectionUtils.isEmpty(supportFileExtensions())){
            return false;
        }
        // trimToEmpty 方法：如果字符串在修剪后为空串（""）或 null，则从该字符串的两端删除控制字符，并返回空串
        var ext = StringUtils.trimToEmpty(
                FilenameUtils.getExtension(path.getFileName().toString())
        );
        return supportFileExtensions()
                .stream()
                .anyMatch(ext::equalsIgnoreCase);
    }
}
