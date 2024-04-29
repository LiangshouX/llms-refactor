package com.liangshou.llmsrefactor.document;

import com.liangshou.llmsrefactor.document.importer.config.DocumentImporterModuleConfiguration;
import com.liangshou.llmsrefactor.document.indexer.DocumentIndexerModuleConfiguration;
import com.liangshou.llmsrefactor.document.loader.DocumentLoaderModuleConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.Executor;

/**
 * @author X-L-S
 */
@Configuration
@Import({
        DocumentIndexerModuleConfiguration.class,
        DocumentLoaderModuleConfiguration.class,
        DocumentImporterModuleConfiguration.class,
})
@EnableAsync
public class DocumentModuleConfiguration implements AsyncConfigurer {
    @Override
    public Executor getAsyncExecutor() {
        var executor = new SimpleAsyncTaskExecutor();
        executor.setVirtualThreads(true);
        return executor;
    }
}
