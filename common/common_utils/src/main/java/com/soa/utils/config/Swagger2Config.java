package com.soa.utils.config;

import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @ program: demo
 * @ description: The config class of swagger
 * @ author: ShenBo
 * @ date: 2021-11-15 23:50:45
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {

    @Bean
    public Docket webApiConfig(){
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("webApi")
                .apiInfo(webApiInfo())
                .select()
                .paths(Predicates.not(PathSelectors.regex("/admin/.*")))
                .paths(Predicates.not(PathSelectors.regex("/error.*")))
                .build();

    }

    private ApiInfo webApiInfo(){

        return new ApiInfoBuilder()
                .title("网站-挂号系统API文档")
                .description("本文档描述了挂号系统微服务接口定义")
                .version("1.0")
                .contact(new Contact("tongji", "http://www.tongji.edu.cn", "10000@qq.com"))
                .build();
    }
}
