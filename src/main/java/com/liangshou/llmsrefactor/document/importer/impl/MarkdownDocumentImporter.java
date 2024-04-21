package com.liangshou.llmsrefactor.document.importer.impl;

import com.liangshou.llmsrefactor.document.importer.DocumentImporter;
import com.liangshou.llmsrefactor.document.importer.exception.DocumentImportException;
import com.liangshou.llmsrefactor.knowlefgebase.entity.KnowledgeBaseArticle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.util.List;
import java.util.Set;

/**
 * @author X-L-S
 */
public class MarkdownDocumentImporter implements DocumentImporter {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Override
    public List<KnowledgeBaseArticle> importDocuments(Path path)
            throws DocumentImportException {
        return null;
    }

    @Override
    public Set<String> supportFileExtensions() {
        return Set.of("md");
    }
}
