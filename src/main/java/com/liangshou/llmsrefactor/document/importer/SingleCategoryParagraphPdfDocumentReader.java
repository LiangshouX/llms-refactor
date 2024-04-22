package com.liangshou.llmsrefactor.document.importer;

import org.springframework.ai.document.Document;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.reader.pdf.ParagraphPdfDocumentReader;
import org.springframework.ai.reader.pdf.config.ParagraphManager;
import org.springframework.ai.reader.pdf.config.PdfDocumentReaderConfig;
import org.springframework.core.io.PathResource;

import java.nio.file.Path;
import java.util.Optional;

import static org.springframework.ai.reader.pdf.config.PdfDocumentReaderConfig.ALL_PAGES;

/**
 * 对单个 PDF 文件根据一级大纲（类别）进行读取
 *  把整个 PDF 文档读取成一个 document对象
 * @author X-L-S
 */
public class SingleCategoryParagraphPdfDocumentReader
        extends ParagraphPdfDocumentReader {

    private final Path path;

    public SingleCategoryParagraphPdfDocumentReader(Path path){
        super(new PathResource(path));
        this.path = path;
    }

    @Override
    public String getTextBetweenParagraphs(
            ParagraphManager.Paragraph fromParagraph, ParagraphManager.Paragraph toParagraph) {
        var reader = new PagePdfDocumentReader(new PathResource(path),
                PdfDocumentReaderConfig
                        .builder()
                        .withPagesPerDocument(ALL_PAGES)
                        .build());
        var documents = reader.get();

        return Optional.ofNullable(documents.get(0))
                .map(Document::getContent)
                .map(String::trim)
                .orElse("");
    }
}
