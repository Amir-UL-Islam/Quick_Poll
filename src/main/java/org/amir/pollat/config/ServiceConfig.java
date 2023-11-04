package org.amir.pollat.config;

import lombok.RequiredArgsConstructor;
import org.amir.pollat.model.entity.Roles;
import org.amir.pollat.model.entity.Users;
import org.amir.pollat.services.UserServices;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@Configuration
@RequiredArgsConstructor
// Use @AllArgsConstructor  to generate a constructor for all of your class's fields and use @RequiredArgsConstructor to generate a constructor for all class's fields that are marked as final.

public class ServiceConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    CommandLineRunner run(UserServices userServices) {
        return args -> {
            // Adding Users
            userServices.saveUser(new Users(
                            null,
                            "Amir",
                            "Islam",
                            "1",
                            new ArrayList<>()
                    )
            );
            userServices.saveUser(new Users(
                            null,
                            "Ibrahim",
                            "Hassan",
                            "1",
                            new ArrayList<>()
                    )
            );

            // Adding all Available Roles
            userServices.saveRole(new Roles(
                            null,
                            "ROLE_ADMIN"
                    )
            );
            userServices.saveRole(new Roles(
                            null,
                            "ROLE_USER"
                    )
            );
            userServices.saveRole(new Roles(
                            null,
                            "ROLE_MODERATOR"
                    )
            );


            // Adding Roles to Users
            userServices.addRoleToUser("Amir", "ROLE_ADMIN");
            userServices.addRoleToUser("Amir", "ROLE_MODERATOR");
            userServices.addRoleToUser("Ibrahim", "ROLE_USER");


        };

    }
}
