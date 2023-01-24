package org.amir.pollat.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.inject.Inject;


// Enabling the Security
@EnableWebSecurity

// For method Level Security
//@EnableGlobalMethodSecurity(prePostEnabled = true)
// Multiple Sources
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Inject
    private Quick_PollUserDetailsService quick_pollUserDetailsService;
    protected String[] PERMIT_ALL= {
            "/api/polls/**",
            "/api/users/**",
            "/swagger-ui/**",
            "/swagger-resources/**",
            "/api-docs/**",
            "/v2/api-docs/**",
            "/v3/api-docs/**",
            "/webjars/**"
    };

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(quick_pollUserDetailsService);

    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        super.configure(http);
        http
                .authorizeRequests()
                .antMatchers(PERMIT_ALL).permitAll()
                .anyRequest().authenticated()
                .and()
                .csrf().disable()
                .httpBasic();
    }
}




