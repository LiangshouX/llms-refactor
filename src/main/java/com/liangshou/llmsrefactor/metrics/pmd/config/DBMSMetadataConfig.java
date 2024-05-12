package com.liangshou.llmsrefactor.metrics.pmd.config;

import net.sourceforge.pmd.util.database.DBMSMetadata;
import net.sourceforge.pmd.util.database.DBURI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;

import javax.sql.DataSource;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * 提供 DBMSMetadata 的初始化配置
 * <p>
 *     采用单例模式构建 DBMSMetadata 对象，避免频繁的数据库连接建立与销毁
 * </p>
 * @author X-L-S
 */
@Deprecated
// @Configuration
public class DBMSMetadataConfig {

    DataSource dataSource;  // 由 Spring 管理的数据源

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${spring.datasource.username:root}")
    String user;

    @Value("${spring.datasource.password:root}")
    String password;

    @Value("${spring.datasource.url:jdbc:mysql://localhost:3306/llm_refactor}")
    String url;

    // @Autowired
    public DBMSMetadataConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    /**
     *
     * @return DBMSMetadata 实例
     * @throws SQLException SQL 异常
     */
    @Lazy
    @Bean
    public DBMSMetadata pmdDbMetadata () throws SQLException {
        Connection connection = dataSource.getConnection();

        DBURI dburi = constructDBURI(url);

        try {
            return new DBMSMetadata(user, password, dburi);
        } catch (MalformedURLException | ClassNotFoundException e) {
            logger.error("Build DBMSMetadata failed in class {}", DBMSMetadataConfig.class.toString());
            throw new RuntimeException(e);
        }
    }

    /**
     * 辅助方法，构建适合 PMD 的 DBURI 实例
     * @param url 数据库地址
     * @return 构建 DBMSMetadata 所需要的 DBURI
     * @throws SQLException SQL异常
     */
    private DBURI constructDBURI (String url) throws SQLException {
        try {
            return new DBURI(url);
        } catch (URISyntaxException | IOException e) {
            logger.error("Build DBURI failed in class {}", DBMSMetadataConfig.class.toString());
            throw new RuntimeException(e);
        }
    }
}
