//package org.amir.pollat.swagger;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.service.ApiInfo;
//import springfox.documentation.service.Contact;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//
//import java.util.Collections;
//
//import static org.apache.tomcat.jni.Address.getInfo;
//import static org.springframework.web.servlet.mvc.method.RequestMappingInfo.paths;
//
//@Configuration
//public class QuickPollApi {
//       @Bean
//    public Docket api() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .select()
//                .apis(RequestHandlerSelectors.any())
//                .paths(PathSelectors.any())
//                .build()
//                .apiInfo(getInfo());
//
//    }
//
//    private ApiInfo getInfo() {
//
//           return new ApiInfo(
//                   "Quick Poll API",
//                   "Quick Poll API",
//                   "1.0",
//                   "Terms of Service",
//                   new Contact("Amir", "https://www.amir.org", "amir@mailservice.com"),
////                   "Amir",
//                   "Apache License Version 2.0",
//                   "https://www.apache.org/licenses/LICENSE-2.0",
//                   Collections.emptyList()
//           );
//    }
//}
