package org.amir.pollat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication
@EnableJpaRepositories(basePackages = "org.amir.pollat.repository")
public class PollAtApplication {

    public static void main(String[] args) {
        SpringApplication.run(PollAtApplication.class, args);
    }

}
