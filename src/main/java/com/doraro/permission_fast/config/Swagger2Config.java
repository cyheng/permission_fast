package com.doraro.permission_fast.config;

import com.doraro.permission_fast.util.Constant;
import com.google.common.collect.Lists;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

import static com.doraro.permission_fast.util.Constant.TOKEN_PREFIX;

@Configuration
@EnableSwagger2
public class Swagger2Config {


    @Bean
    public Docket createRestApi() {
        final Parameter parameter = new ParameterBuilder()
                .name("Authorization").description("登录校验")
                .modelRef(new ModelRef("string")).parameterType("header")
                .required(false).defaultValue(TOKEN_PREFIX).build();
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.doraro.permission_fast.controller"))
                .paths(PathSelectors.any())
                .build()

                .globalOperationParameters(Lists.newArrayList(parameter));
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("后台管理手脚架")
                .description("permission_fast文档")
                .version("0.0.1")
                .build();
    }

}
