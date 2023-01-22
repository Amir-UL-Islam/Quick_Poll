package org.amir.pollat.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.inject.Inject;

@Configuration

// Enabling the Security
@EnableWebSecurity

@EnableWebMvc

// For method Level Security
@EnableGlobalMethodSecurity(prePostEnabled = true)

////Book Version
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//    protected String[] PERMIT_ALL= {
//            "/swagger-ui/**",
//            "/swagger-resources/**",
//            "/api-docs/**",
//            "/v2/**",
//            "/v3/**",
//            "/webjars/**"
//    };
//    @Inject
//    private UserDetailsService userDetailsService;
//    @Override
//    protected void  configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception{
//        authenticationManagerBuilder
//                .userDetailsService(userDetailsService)
//                .passwordEncoder(new BCryptPasswordEncoder());
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception{
//        http
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .authorizeRequests()
//                .antMatchers(PERMIT_ALL).permitAll()
//                .antMatchers("/polls/**").authenticated()
//                .and()
//                .httpBasic()
//                .realmName("Poll At")
//                .and()
//                .csrf().disable();
//    }
//}





// Multiple Sources
public class SecurityConfig extends WebSecurityConfigurerAdapter {
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


//        Copilot Suggestions
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        Copilot Version
//        http
//                .authorizeRequests(authorizeRequests ->
//                        authorizeRequests
//                                .antMatchers(PERMIT_ALL).permitAll()
//                                .anyRequest().authenticated()
//                )
//                .httpBasic(basic -> {})
//                .csrf(csrf -> csrf.disable());



//        Basic Version

//        http
//                .authorizeRequests()
//                .antMatchers("api/polls/**").permitAll()
//                .anyRequest()
//                .authenticated()
//                .and()
//                .httpBasic()
//                .and()
//                .formLogin();
//        return http.build();
//    }




//    JavaBrain Version
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        super.configure(auth);

        auth.inMemoryAuthentication()
                .withUser("user")
                .password("user")
                .roles("USER")

        // Adding multiple User
                .and()
                .withUser("admin")
                .password("admin")
                .roles("ADMIN");

    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
//        return new BCryptPasswordEncoder();
        return NoOpPasswordEncoder.getInstance();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        super.configure(http);
        http.authorizeRequests()
                .antMatchers("/**")

                .hasRole("USER")

//                or I can Allow to Any Role to This Endpoint
//                .hasAnyRole()

                .and()
//                .formLogin();
                .httpBasic();
    }
}




