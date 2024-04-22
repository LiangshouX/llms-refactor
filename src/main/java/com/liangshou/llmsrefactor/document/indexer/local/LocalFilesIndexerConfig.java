package com.liangshou.llmsrefactor.document.indexer.local;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 配置项，指定文件索引的目录
 *
 * @author X-L-S
 */
@ConfigurationProperties(prefix = "document.index.local-files")
public record LocalFilesIndexerConfig (String baseDir){
}
