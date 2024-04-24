package com.liangshou.llmsrefactor.document.loader;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.splitter.DocumentSplitters;
import dev.langchain4j.data.segment.TextSegment;
import org.springframework.ai.transformer.splitter.TextSplitter;

import java.util.List;

/**
 * 文档切分工具类
 * @author X-L-S
 */
public class RecursiveTextSplitter extends TextSplitter {
    @Override
    protected List<String> splitText(String text) {
        var splitter = DocumentSplitters.recursive(800, 100);
        return splitter.split(new Document(text))
                .stream()
                .map(TextSegment::text)
                .toList();
    }
}
