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
        // TODO 完善业务逻辑代码
        var options = new MutableDataSet();
        options.set(Parser.EXTENSIONS, List.of(YamlFrontMatterExtension.create()));
        var parser = Parser.builder(options).build();
        var topic = FilenameUtils.getBaseName(path.getFileName().toString());
        try {
            var doc = parser.parse(Files.readString(path));
            // 每条记录的四个属性和内容
            String category = null;
            String subCategory = null;
            String level = null;
            String itemId = null;
            StringBuilder content = new StringBuilder();

            List<KnowledgeBaseArticle> articles = new ArrayList<>();

            // 遍历每一个子节点（md 标题）
            for(Node child:doc.getChildren()){
                if(child instanceof YamlFrontMatterBlock yamlFrontMatterBlock){
                    for (Node blockChild : yamlFrontMatterBlock.getChildren()) {
                        if (blockChild instanceof YamlFrontMatterNode blockNode
                                && Objects.equals("topic", blockNode.getKey())
                                && blockNode.getFirstChild() != null) {
                            topic = blockNode.getFirstChild().getChars().toString();
                        }
                    }
                }
                // 处理各个标题
                else if(child instanceof Heading heading){
                    // 一级标题，代表 category
                    if(heading.getLevel() == 1){
                        category = heading.getText().toString();
                    }
                    else if (heading.getLevel() == 2){
                        subCategory = heading.getText().toString();
                    } else if (heading.getLevel() == 3) {
                        if(category != null && subCategory != null
                                && itemId != null && !content.isEmpty()){
                            KnowledgeBaseArticle article = new KnowledgeBaseArticle(category, subCategory,
                                    itemId,StringUtils.trimToEmpty(content.toString()));
                            articles.add(article);
                            content = new StringBuilder();
                        }
                        itemId = heading.getText().toString();
                    }
                }
                // 处理内容
                else {
                    content.append(child.getChars());
                }
            }

            if(!content.isEmpty() && category != null){
                KnowledgeBaseArticle article = new KnowledgeBaseArticle(category, subCategory,
                        itemId,StringUtils.trimToEmpty(content.toString()));
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
