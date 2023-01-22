package org.amir.pollat.swagger;

import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import static org.apache.tomcat.jni.Address.getInfo;
import static org.springframework.web.servlet.mvc.method.RequestMappingInfo.paths;

public class QuickPollApi {
       @Bean
    public Docket myAPI() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .paths(PathSelectors.any())
                .build();
    }


}
