package com.liangshou.llmsrefactor.document.importer.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.liangshou.llmsrefactor.document.importer.DocumentImporters;
import com.liangshou.llmsrefactor.document.importer.impl.JsonDocumentImporter;
import com.liangshou.llmsrefactor.document.importer.impl.MarkdownDocumentImporter;
import com.liangshou.llmsrefactor.document.importer.impl.PdfDocumentImporter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author X-L-S
 */
@Configuration
public class DocumentImporterModuleConfiguration {

    @Bean
    public DocumentImporters documentImporters(
            JsonDocumentImporter jsonDocumentImporter,
            MarkdownDocumentImporter markdownDocumentImporter,
            PdfDocumentImporter pdfDocumentImporter
    ){
        return new DocumentImporters(
                List.of(
                        jsonDocumentImporter,
                        markdownDocumentImporter,
                        pdfDocumentImporter
                )
        );
    }

    @Bean
    public JsonDocumentImporter jsonDocumentImporter(ObjectMapper objectMapper){
        return new JsonDocumentImporter(objectMapper);
    }

    @Bean
    public MarkdownDocumentImporter markdownDocumentImporter(){
        return new MarkdownDocumentImporter();
    }

    @Bean
    public PdfDocumentImporter pdfDocumentImporter(){
        return new PdfDocumentImporter();
    }
}
