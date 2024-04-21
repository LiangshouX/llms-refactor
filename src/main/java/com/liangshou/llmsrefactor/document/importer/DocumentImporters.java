package com.liangshou.llmsrefactor.document.importer;

import com.liangshou.llmsrefactor.document.importer.exception.DocumentImportException;
import com.liangshou.llmsrefactor.knowlefgebase.entity.KnowledgeBaseArticle;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author X-L-S
 */
public class DocumentImporters {
    private final List<DocumentImporter> documentImporters;

    public DocumentImporters(List<DocumentImporter> documentImporters){
        this.documentImporters = documentImporters;
    }

    public List<KnowledgeBaseArticle> importDocuments(Path path)
            throws DocumentImportException {
        var exceptionRef = new AtomicReference<DocumentImportException>();
        var result = documentImporters.stream()
                .filter(importer -> importer.support(path))
                .findFirst()
                .flatMap(importer -> {
                    try {
                        return Optional.ofNullable(importer.importDocuments(path));
                    }catch (DocumentImportException e){
                        if(exceptionRef.get() == null){
                            exceptionRef.set(e);
                        }
                        else {
                            exceptionRef.updateAndGet(exception -> {
                                exception.addSuppressed(e);
                                return exception;
                            });
                        }
                        return Optional.empty();
                    }
                })
                .orElse(List.of());
        if(result.isEmpty() && exceptionRef.get() != null){
            throw exceptionRef.get();
        }
        return result;
    }
}
