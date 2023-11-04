package org.amir.pollat.security;

import lombok.RequiredArgsConstructor;
import org.amir.pollat.jwtfilters.CustomAuthorizationFilter;
import org.amir.pollat.jwtfilters.CustomFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;


// Enabling the Security
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
// For method Level Security
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
// Multiple Sources
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserDetailsService userDetailsService;
    protected String[] PERMIT_ALL= {
            "/api/users/jwt/save_new_user//**",
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
        // another way of Password Encoding
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }
    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }


    // AUTHORIZATION STEPS
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        CustomFilter customFilter = new CustomFilter(authenticationManagerBean());
        customFilter.setFilterProcessesUrl("/api/v1/login");
//        super.configure(http);
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(PERMIT_ALL).permitAll()
                .antMatchers("/api/v1/login/").permitAll()
                .and()
                .authorizeRequests()
                .antMatchers("/api/v1/user/jwt/**").hasAnyAuthority("ROLE_ADMIN")

//                .anyRequest().permitAll()
                // As I have already Implemented @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('MODERATOR')")
//                .antMatchers("/api/polls/**").hasRole("ADMIN")
//                .antMatchers("/api/polls/{pollId}/votes/").hasRole("USER")
//                .antMatchers("/api/users/**").authenticated()
                .and()
                .authorizeRequests().anyRequest().authenticated()
                .and()
                .addFilter(new CustomFilter(authenticationManager()))
                .addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);


    }
}




