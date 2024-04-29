package com.liangshou.llmsrefactor.document.importer.impl;

import com.liangshou.llmsrefactor.document.importer.DocumentImporter;
import com.liangshou.llmsrefactor.document.importer.exception.DocumentImportException;
import com.liangshou.llmsrefactor.knowlefgebase.entity.KnowledgeBaseArticle;
import com.vladsch.flexmark.ast.Heading;
import com.vladsch.flexmark.ext.yaml.front.matter.YamlFrontMatterBlock;
import com.vladsch.flexmark.ext.yaml.front.matter.YamlFrontMatterExtension;
import com.vladsch.flexmark.ext.yaml.front.matter.YamlFrontMatterNode;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.MutableDataSet;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * @author X-L-S
 */
public class MarkdownDocumentImporter implements DocumentImporter {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Override
    public List<KnowledgeBaseArticle> importDocuments(Path path)
            throws DocumentImportException {
        var options = new MutableDataSet();
        options.set(Parser.EXTENSIONS, List.of(YamlFrontMatterExtension.create()));
        var parser = Parser.builder(options).build();
        var category = FilenameUtils.getBaseName(path.getFileName().toString());
        try {
            var doc = parser.parse(Files.readString(path));
            // 每条记录的属性和内容
            String title = null;
            StringBuilder content = new StringBuilder();

            List<KnowledgeBaseArticle> articles = new ArrayList<>();

            // 遍历每一个子节点（md 标题）
            for(Node child:doc.getChildren()){
                //  1. MD 文档开头的 yaml，记录了该文档的 category，即语言的类别
                if(child instanceof YamlFrontMatterBlock yamlFrontMatterBlock){
                    for (Node blockChild : yamlFrontMatterBlock.getChildren()) {
                        if (blockChild instanceof YamlFrontMatterNode blockNode
                                && Objects.equals("category", blockNode.getKey())
                                && blockNode.getFirstChild() != null) {
                            category = blockNode.getFirstChild().getChars().toString();
                        }
                    }
                }
                // 2. MD 文档的各级标题
                else if(child instanceof Heading heading){
                    // 一级标题，代表 8 个分类之一，不用管
                    if(heading.getLevel() == 1){
                        String headCategory = heading.getText().toString();
                        logger.info("Processing HeadCategory {} in Category {} ....", headCategory, category);
                    }
                    // 二级标题，真实的 title
                    else if (heading.getLevel() == 2){
                        // 搜索到了一个二级标题，需要把之前存的内容构建为对象
                        if (category != null && title != null && !content.isEmpty()) {
                            KnowledgeBaseArticle article = new KnowledgeBaseArticle(category, title,
                                    StringUtils.trimToEmpty(content.toString()));
                            articles.add(article);
                            content = new StringBuilder();
                        }
                        title = heading.getText().toString();

                    }
                }
                // 3. MD 文档的普通内容
                else {
                    content.append(child.getChars());
                }
            }

            if(!content.isEmpty() && category != null){
                KnowledgeBaseArticle article = new KnowledgeBaseArticle(category, title,
                        StringUtils.trimToEmpty(content.toString()));
                articles.add(article);
            }

            return articles;

        } catch (IOException e) {
            logger.error("Failed to import Markdown document {}, skipping",
                    path.toAbsolutePath());
            throw new DocumentImportException(path, e);
        }
    }

    @Override
    public Set<String> supportFileExtensions() {
        return Set.of("md");
    }
}
