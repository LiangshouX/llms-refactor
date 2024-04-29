package com.liangshou.llmsrefactor;

import com.liangshou.llmsrefactor.config.SystemProxyConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class LlmsRefactorApplication {

	public static void main(String[] args) {

//		System.setProperty("java.net.useSystemProxies", "true");
//		System.setProperty("http.proxyHost", "127.0.0.1");
//		System.setProperty("http.proxyPort", "7890");
//		System.setProperty("https.proxyHost", "127.0.0.1");
//		System.setProperty("https.proxyPort", "7890");
//		log.info("Using proxy on port 7890");

		SpringApplication.run(LlmsRefactorApplication.class, args);
	}

}
