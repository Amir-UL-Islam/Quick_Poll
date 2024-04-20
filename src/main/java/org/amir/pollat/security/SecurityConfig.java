package org.amir.pollat.security;

import lombok.RequiredArgsConstructor;
import org.amir.pollat.security.jwtfilters.CustomAuthorizationFilter;
import org.amir.pollat.security.jwtfilters.CustomAuthenticationFilter;
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

    // Permit All These Paths
    protected String[] PERMIT_ALL = {
            "/swagger-ui/**",
            "/swagger-resources/**",
            "/api-docs/**",
            "/v2/api-docs/**",
            "/v3/api-docs/**",
            "/webjars/**",
            "/api/user/create/**",
            "/api/login/**",
            "/api/user/refresh-token/**"
    };

    // AUTHENTICATION
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // another way of Password Encoding
        // loadUserByUsername() method is called by Spring Security to load the user from the database
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
        // Custom Authentication Filter
        // Setting Default URL for Authentication which was /login form UsernamePasswordAuthenticationFilter class
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManagerBean());
        customAuthenticationFilter.setFilterProcessesUrl("/api/login");

//        super.configure(http);
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(STATELESS)

                .and()
                .authorizeRequests().antMatchers(PERMIT_ALL).permitAll()

                .and()
                .authorizeRequests()

                // Dealing with ROLE_BASED AUTHORIZATION
                // As I have already Implemented @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('MODERATOR')")
                .antMatchers("/api/admin-only/**").hasAnyAuthority("ROLE_ADMIN")
                .antMatchers("/api/polls/create/**").hasRole("ADMIN")
                .antMatchers("/api/polls/{pollId}/votes").hasRole("USER")

                //And Now Forcing any User to be Authenticated
                .and()
                .authorizeRequests().anyRequest().authenticated()

                // Adding Custom Filters for Authentication and Authorization
                // Don't Need this Filter as I have already added the CustomAuthenticationFilter And Added Custom URL
//                .addFilter(new CustomAuthenticationFilter(authenticationManager()))
                .and()
                .addFilter(customAuthenticationFilter)
                // Starting Authorization Filter
                // We need Make Sure this Filter is Added Before UsernamePasswordAuthenticationFilter
                // As/if The User have token he/she does not need to login again or Authenticate again
                .addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}




