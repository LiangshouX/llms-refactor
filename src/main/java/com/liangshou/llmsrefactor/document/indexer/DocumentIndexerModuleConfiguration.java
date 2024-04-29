package com.liangshou.llmsrefactor.document.indexer;

import com.liangshou.llmsrefactor.document.importer.DocumentImporters;
import com.liangshou.llmsrefactor.document.importer.config.DocumentImporterModuleConfiguration;
import com.liangshou.llmsrefactor.document.indexer.local.LocalFilesIndexer;
import com.liangshou.llmsrefactor.document.indexer.local.LocalFilesIndexerConfig;
import com.liangshou.llmsrefactor.document.indexer.local.LocalFilesWatcher;
import com.liangshou.llmsrefactor.knowlefgebase.KnowledgeBaseModuleConfiguration;
import com.liangshou.llmsrefactor.knowlefgebase.KnowledgeBaseService;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author X-L-S
 */
@Configuration
@EnableConfigurationProperties({
        LocalFilesIndexerConfig.class
})
@Import({DocumentImporterModuleConfiguration.class,
        KnowledgeBaseModuleConfiguration.class})
public class DocumentIndexerModuleConfiguration {

    @Bean
    public LocalFilesIndexer localFilesIndexer(
            DocumentImporters documentImporters,
            KnowledgeBaseService knowledgeBaseService,
            LocalFilesIndexerConfig config) {
        return new LocalFilesIndexer(documentImporters, knowledgeBaseService, config);
    }

    @Bean
    public LocalFilesWatcher localFilesWatcher(LocalFilesIndexer indexer) {
        return new LocalFilesWatcher(indexer);
    }

    @Bean
    public IndexerAppStartupListener indexAppStartupListener(
            LocalFilesIndexerConfig localFilesIndexerConfig,
            DocumentIndexer documentIndexer,
            LocalFilesWatcher localFilesWatcher) {
        return new IndexerAppStartupListener(localFilesIndexerConfig,
                documentIndexer,
                localFilesWatcher);
    }

}
