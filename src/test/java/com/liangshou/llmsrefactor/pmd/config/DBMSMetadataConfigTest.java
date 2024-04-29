package com.liangshou.llmsrefactor.pmd.config;

import net.sourceforge.pmd.util.database.DBMSMetadata;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

/**
 * @author X-L-S
 */
@Deprecated
@ExtendWith(MockitoExtension.class)
@SpringBootTest
class DBMSMetadataConfigTest {

    @InjectMocks
    private DBMSMetadataConfig config;

    @Mock
    private Environment env;

    @Mock
    private DataSource dataSource;

    @Mock
    private Connection connection;

    @Test
    void testPropertyInjection() {
        // 设置模拟的环境属性
        Map<String, Object> properties = new HashMap<>();
        properties.put("spring.datasource.url", "jdbc:mysql://localhost:3306/test");
        properties.put("spring.datasource.username", "testUser");
        properties.put("spring.datasource.password", "testPass");
        when(env.getProperty(anyString())).thenAnswer(invocation -> properties.get(invocation.getArgument(0)));

        // 验证属性注入
        assertNotNull(config.user);
        assertEquals("jdbc:mysql://localhost:3306/test", config.url);
        assertEquals("testUser", config.user);
        assertEquals("testPass", config.password);
    }

    @Test
    void testDataSourceInjection() {
        // 验证DataSource注入
        assertNotNull(config.dataSource);
    }

    @Test
    void testDBMSMetadataCreation() throws SQLException {
        // 模拟DataSource获取连接
        when(dataSource.getConnection()).thenReturn(connection);

        // 创建并验证DBMSMetadata实例
        DBMSMetadata metadata = config.pmdDbMetadata();
        assertNotNull(metadata);
    }

    @Test
    void testDatabaseConnection() throws SQLException {
        // 使用真实的DataSource，进行集成测试
        DataSource realDataSource = DataSourceBuilder.create()
                .url("jdbc:mysql://localhost:3306/test")
                .username("testUser")
                .password("testPass")
                .build();

        config.dataSource = realDataSource;
        Connection conn = config.dataSource.getConnection();

        // 执行简单的查询以验证连接有效性
        // ...此处省略具体的SQL查询和结果验证...

        conn.close();
    }
}