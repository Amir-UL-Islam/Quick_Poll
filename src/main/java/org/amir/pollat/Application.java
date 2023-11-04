package org.amir.pollat;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

//@SecurityScheme(
//        name = "bearerAuth",
//        scheme = "bearer",
//        bearerFormat = "JWT",
//        type = SecuritySchemeType.HTTP,
//        in = SecuritySchemeIn.HEADER
//)
//@SecurityRequirement(name = "bearerAuth")
@EnableAsync
@EnableSwagger2
@SpringBootApplication
//@EnableJpaRepositories(basePackages = "org.amir.pollat.repository")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
