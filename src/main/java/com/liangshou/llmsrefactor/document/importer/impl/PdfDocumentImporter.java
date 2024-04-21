package com.liangshou.llmsrefactor.document.importer.impl;

import com.liangshou.llmsrefactor.document.importer.DocumentImporter;
import com.liangshou.llmsrefactor.document.importer.SingleCategoryParagraphPdfDocumentReader;
import com.liangshou.llmsrefactor.document.importer.exception.DocumentImportException;
import com.liangshou.llmsrefactor.knowlefgebase.entity.KnowledgeBaseArticle;
import org.springframework.ai.document.Document;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * pdf 文档导入工具类
 * @author X-L-S
 */
public class PdfDocumentImporter implements DocumentImporter {
    @Override
    public List<KnowledgeBaseArticle> importDocuments(Path path)
            throws DocumentImportException {
        try {
            var documents = importWithParagraph(path);
            // var category = FilenameUtils.getBaseName(path.getFileName().toString());
            return documents.stream().map(
                    // TODO 补全代码
                    document ->
                        new KnowledgeBaseArticle(
                                getMetaProperty(document, "category", false),
                                getMetaProperty(document, "subCategory", false),
                                getMetaProperty(document, "level", false),
                                document.getContent(),
                                getMetaProperty(document, "positiveExample", true),
                                getMetaProperty(document, "counterExample", true),
                                getMetaProperty(document, "note", true)
                        )

            ).toList();
        }catch (Exception e){
            throw new DocumentImportException(path, e);
        }
    }

    @Override
    public Set<String> supportFileExtensions() {
        return Set.of("pdf");
    }

    @Override
    public boolean support(Path path) {
        return DocumentImporter.super.support(path);
    }

    private List<Document> importWithParagraph(Path path){
        return new SingleCategoryParagraphPdfDocumentReader(path).get();
    }

    /*************** 从 document 中依次获取到文档的7个属性 ****************/

    private String getMetaProperty(Document document, String meta, boolean nullable){
        String metaProperty = (String) document.getMetadata().get(meta);
        if(!nullable){
            return Optional.ofNullable(metaProperty).orElseGet(
                    () -> document.getContent().substring(0, 20)
            );
        }
        else{
            return Optional.ofNullable(metaProperty).orElseGet(
                    () -> ""
            );
        }
    }
}
