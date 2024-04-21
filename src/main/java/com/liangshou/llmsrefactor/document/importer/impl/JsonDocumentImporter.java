package com.liangshou.llmsrefactor.document.importer.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.liangshou.llmsrefactor.document.importer.DocumentImporter;
import com.liangshou.llmsrefactor.document.importer.exception.DocumentImportException;
import com.liangshou.llmsrefactor.knowlefgebase.entity.KnowledgeBaseArticle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;

/**
 * JSON 文档导入工具类
 * @author X-L-S
 */
public class JsonDocumentImporter implements DocumentImporter {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final ObjectMapper objectMapper;

    public JsonDocumentImporter(ObjectMapper objectMapper){
        this.objectMapper = objectMapper;
    }
    @Override
    public List<KnowledgeBaseArticle> importDocuments(Path path)
            throws DocumentImportException {
        try {
            var article = objectMapper.readValue(path.toFile(), KnowledgeBaseArticle.class);
            return List.of(article);
        }catch (IOException e){
            logger.error("Failed to import JSON document {}, skipping", path.toAbsolutePath());
            throw new DocumentImportException(path, e);
        }
    }

    @Override
    public Set<String> supportFileExtensions() {
        return Set.of("json");
    }
}
