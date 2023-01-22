package org.amir.pollat;

//import org.amir.pollat.Security.SecurityConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication
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
