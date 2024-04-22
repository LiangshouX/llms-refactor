package com.liangshou.llmsrefactor.document.indexer.local;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.*;

import static java.nio.file.StandardWatchEventKinds.OVERFLOW;

/**
 * 调用文件系统提供的文件监视服务
 *
 * @author X-L-S
 */
public class LocalFilesWatcher {
    private final LocalFilesIndexer indexer;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public LocalFilesWatcher(LocalFilesIndexer indexer){
        this.indexer = indexer;
    }

    public void watch(Path baseDir){
        // TODO 完善线程逻辑
        try {
            doWatch(baseDir);
        }
        catch (IOException e){
            logger.error("Failed to watch for changes", e);
        }
    }

    private void doWatch(Path path) throws IOException {
        logger.info("Watching {} for changes", path);
        try (var watchService = FileSystems.getDefault().newWatchService()) {
            path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);
            while (true) {
                WatchKey key;
                try {
                    key = watchService.take();
                } catch (InterruptedException e) {
                    break;
                }
                for (WatchEvent<?> event : key.pollEvents()) {
                    var kind = event.kind();
                    if (kind == OVERFLOW) {
                        continue;
                    }
                    // 发现新文件时，调用 indexer 来进行索引
                    WatchEvent<Path> watchEvent = (WatchEvent<Path>) event;
                    indexer.index(path.resolve(watchEvent.context()));
                }
                if (!key.reset()) {
                    break;
                }
            }
        }
    }
}
