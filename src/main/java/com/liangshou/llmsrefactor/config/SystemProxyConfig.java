package com.liangshou.llmsrefactor.config;

import jakarta.annotation.Resource;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.*;
import java.util.Collections;

/**
 * 系统代理相关的配置。因为调用 ChatGPT 的接口可能存在墙的问题，需要进行一些处理
 *
 * @author X-L-S
 */
@Getter
@Component
@ConfigurationProperties(prefix = "system-proxy")
class SystemProxySettings {
    private String url;
    private int port;
    private boolean enable;

    public void setUrl(String url) {
        this.url = url;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }
}

@Component
@Slf4j
public class SystemProxyConfig implements ApplicationRunner {
    @Resource
    private SystemProxySettings systemProxySettings;

    @Override
    public void run(ApplicationArguments args) {
        if (systemProxySettings.isEnable()) {
            setSystemProxy();
        }
    }

    private void setSystemProxy() {
        System.setProperty("java.net.useSystemProxies", "true");
        System.setProperty("http.proxyHost", systemProxySettings.getUrl());
        System.setProperty("http.proxyPort", String.valueOf(systemProxySettings.getPort()));
        System.setProperty("https.proxyHost", systemProxySettings.getUrl());
        System.setProperty("https.proxyPort", String.valueOf(systemProxySettings.getPort()));

        ProxySelector.setDefault(new ProxySelector() {
            @Override
            public java.util.List<Proxy> select(URI uri) {
                return Collections.singletonList(new Proxy(Proxy.Type.HTTP,
                        new InetSocketAddress(systemProxySettings.getUrl(), systemProxySettings.getPort())));
            }

            @Override
            public void connectFailed(URI uri, SocketAddress sa, IOException ioe) {
                if (uri == null || sa == null || ioe == null) {
                    throw new IllegalArgumentException("Arguments can't be null.");
                }
            }
        });

        log.info("Using System Proxy: " + System.getProperty("http.proxyHost") +
                ":" + System.getProperty("http.proxyPort"));
    }
}