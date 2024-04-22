package com.liangshou.llmsrefactor.document.indexer.local;

import com.liangshou.llmsrefactor.document.importer.DocumentImporters;
import com.liangshou.llmsrefactor.document.indexer.DocumentIndexer;
import com.liangshou.llmsrefactor.knowlefgebase.KnowledgeBaseService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 基于本地文件系统的 DocumentIndexer 的实现
 * @author X-L-S
 */
public class LocalFilesIndexer implements DocumentIndexer {

    private final DocumentImporters documentImporters;

    private final KnowledgeBaseService knowledgeBaseService;

    private final LocalFilesIndexerConfig config;

    /**
     * 创建两个目录，分别存放处理过的文件和出错的文件
     */
    private final String processedDir = ".processed";

    private final String errorDir = ".error";

    // TODO 完善线程池逻辑
    private final ExecutorService executorService = Executors.newCachedThreadPool();

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public LocalFilesIndexer(
            DocumentImporters documentImporters,
            KnowledgeBaseService knowledgeBaseService,
            LocalFilesIndexerConfig config
    ){
        this.documentImporters = documentImporters;
        this.knowledgeBaseService = knowledgeBaseService;
        this.config = config;

        var baseDir = Paths.get(config.baseDir());
        for(String dir: List.of(processedDir, errorDir)){
            try {
                Files.createDirectories(baseDir.resolve(dir));
            }catch (IOException e){
                throw new RuntimeException("Failed to create directory %S.".formatted(dir), e);
            }
        }
    }
    @Override
    public void index() {
        var baseDir = Paths.get(config.baseDir());
        index(baseDir);
    }

    public void index(Path path){
        if (path.toFile().isDirectory()) {
            logger.info("Indexing documents in {}", path.toAbsolutePath());
            try {
                indexDirectory(path);
            } catch (IOException e) {
                logger.error("Failed to index directory {}", path, e);
            }
        } else if (path.toFile().isFile()) {
            logger.info("Indexing file {}", path.toAbsolutePath());
            indexFile(path);
        }
    }


    private void indexDirectory(Path baseDir) throws IOException {
        try (var directoryStream = Files.newDirectoryStream(baseDir,
                path -> path.toFile().isFile())) {
            directoryStream.forEach(this::indexFile);
        }
    }

    private void indexFile(Path path) {
        executorService.submit(() -> {
            try {
                var articles = documentImporters.importDocuments(path);
                knowledgeBaseService.importArticles(articles);
                moveFile(path, true);
            } catch (Exception e) {
                logger.error("Failed to import file {}", path, e);
                moveFile(path, false);
            }
        });
    }

    private void moveFile(Path path, boolean success) {
        var subDir = success ? processedDir : errorDir;
        var filename = path.getFileName().toString();
        var baseName = FilenameUtils.getBaseName(filename);
        var ext = FilenameUtils.getExtension(filename);
        var updatedName = "%s-%s-%s".formatted(baseName, UUID.randomUUID(), ext);
        // var updatedName = STR."\{baseName}-\{UUID.randomUUID()}.\{ext}";
        var updatedPath = path.getParent().resolve(subDir).resolve(updatedName);
        try {
            FileUtils.moveFile(path.toFile(), updatedPath.toFile());
        } catch (IOException e) {
            logger.error("Failed to move file from {} to {}", path, updatedPath, e);
        }
    }
}
