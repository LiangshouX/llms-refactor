package com.liangshou.llmsrefactor.document.indexer;

import com.liangshou.llmsrefactor.document.indexer.local.LocalFilesIndexer;
import com.liangshou.llmsrefactor.document.indexer.local.LocalFilesIndexerConfig;
import com.liangshou.llmsrefactor.document.indexer.local.LocalFilesWatcher;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 当应用启动时，通过DocumentIndexer 来执行一次索引，
 *  同时启动监视文件系统的 LocalFilesWatcher
 *
 * @author X-L-S
 */
public class IndexerAppStartupListener implements
        ApplicationListener<ApplicationStartedEvent> {

    private final LocalFilesIndexerConfig localFilesIndexerConfig;

    private final DocumentIndexer documentIndexer;

    private final LocalFilesWatcher localFilesWatcher;

    public IndexerAppStartupListener(
            LocalFilesIndexerConfig localFilesIndexerConfig,
            DocumentIndexer documentIndexer,
            LocalFilesWatcher localFilesWatcher
    ){
        this.localFilesIndexerConfig = localFilesIndexerConfig;
        this.documentIndexer = documentIndexer;
        this.localFilesWatcher = localFilesWatcher;
    }
    @Override
    public void onApplicationEvent(@NotNull ApplicationStartedEvent event) {
        documentIndexer.index();
        localFilesWatcher.watch(Paths.get(localFilesIndexerConfig.baseDir()));
    }
}
