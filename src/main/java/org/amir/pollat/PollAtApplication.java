package org.amir.pollat;

import org.amir.pollat.entity.Roles;
import org.amir.pollat.entity.Users;
import org.amir.pollat.services.UserServices;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@EnableSwagger2
@SpringBootApplication
//@EnableJpaRepositories(basePackages = "org.amir.pollat.repository")
public class PollAtApplication {

    public static void main(String[] args) {
        SpringApplication.run(PollAtApplication.class, args);
    }

}
