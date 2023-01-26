package org.amir.pollat.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.inject.Inject;


// Enabling the Security
@Configuration
@EnableWebSecurity

// For method Level Security
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
// Multiple Sources
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Inject
    private Quick_PollUserDetailsService quick_pollUserDetailsService;
    protected String[] PERMIT_ALL= {
            "/api/users/newUser/**",
            "/swagger-ui/**",
            "/swagger-resources/**",
            "/api-docs/**",
            "/v2/api-docs/**",
            "/v3/api-docs/**",
            "/webjars/**"
    };

    // AUTHENTICATION
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(quick_pollUserDetailsService);

    }

    @Bean
    public BCryptPasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }


    // AUTHORIZATION STEPS
    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        super.configure(http);
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(PERMIT_ALL).permitAll()
                // As I have already Implemented @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('MODERATOR')")
//                .antMatchers("/api/polls/**").hasRole("ADMIN")
//                .antMatchers("/api/polls/{pollId}/votes/").hasRole("USER")
                .antMatchers("/api/users/**").authenticated()
                .and()
                .httpBasic();
    }
}




