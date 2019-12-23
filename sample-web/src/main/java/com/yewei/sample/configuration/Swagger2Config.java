package com.yewei.sample.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.ApiSelectorBuilder;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@ConditionalOnClass(Docket.class)
@Conditional(Swagger2Condition.class)
public class Swagger2Config {
    @Configuration
    @EnableSwagger2
    protected class Swagger2Load {
        @Value("${swagger.header.add:false}")
        private boolean needAddHeader;

        @Bean
        public Docket createRestApi() {// 创建文档生成器
            ApiSelectorBuilder asb = new Docket(DocumentationType.SWAGGER_2).apiInfo(getApiInfo()).select();
            asb.apis(RequestHandlerSelectors.withClassAnnotation(RestController.class));
            asb.paths(PathSelectors.any());

            //添加head参数start
            List<Parameter> headers = new ArrayList<>();
            ParameterBuilder userIdHeader = new ParameterBuilder();
            userIdHeader.name("x-user-id").description("user id").modelRef(new ModelRef("string")).parameterType("header").required(false);
            headers.add(userIdHeader.build());
            ParameterBuilder grayEnvHeader = new ParameterBuilder();
            grayEnvHeader.name("x-gray-env").description("env flag").modelRef(new ModelRef("string")).parameterType("header").required(false).defaultValue("normal");
            headers.add(grayEnvHeader.build());
            //添加head参数end

            Docket docket = asb.build();
            docket.groupName("all");
            if(needAddHeader){
                docket.globalOperationParameters(headers);
            }
            return docket;
        }

        private ApiInfo getApiInfo() {
            return new ApiInfoBuilder().title("API").version(this.getClass().getPackage().getImplementationVersion())
                    .build();
        }
        
        @Controller
        @ApiIgnore
        @RequestMapping(value = "/docs")
        public class DocumentController {

            @RequestMapping()
            public String apis() {
                return "redirect:/swagger-ui.html";
            }
        }
    }



}
