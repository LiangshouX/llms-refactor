package com.liangshou.llmsrefactor.config;

import net.sf.jsqlparser.expression.operators.arithmetic.Concat;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Knife4j 接口文档配置
 *      <a href="https://doc.xiaominfo.com/knife4j/documentation/get_start.html">...</a>
 *
 * @author X-L-S
 */
@Configuration
@EnableSwagger2
@Profile({"dev", "prod"})   // 版本控制访问
public class Knife4jConfig {
    @Bean(value = "defaultApi2")
    public Docket defaultApi2(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.liangshou.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * 自定义接口文档信息
     */
    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                // 接口文档的标题
                .title("大语言模型对话系统-llm-refactor")
                // 接口文档的描述信息
                .description("llm-refactor")
                .contact(new Contact("liangshou", "github.com/LiangshouX", "3177065496@qq.com"))
                .version("1.0")
                .build();
    }

}
