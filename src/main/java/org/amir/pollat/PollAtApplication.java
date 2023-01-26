package org.amir.pollat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication
@EnableJpaRepositories(basePackages = "org.amir.pollat.repository")
// For disabling the security config
//@ComponentScan(
//        basePackages = { "org.amir" },
//        excludeFilters = {
//                @ComponentScan.Filter(
//                        type = FilterType.ASSIGNABLE_TYPE,
//                        value = { SecurityConfig.class })
//        })
public class PollAtApplication {

    public static void main(String[] args) {
        SpringApplication.run(PollAtApplication.class, args);
    }

}
