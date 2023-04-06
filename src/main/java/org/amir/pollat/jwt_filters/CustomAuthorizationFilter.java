package org.amir.pollat.jwt_filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.amir.pollat.security.SecurityConfig;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static java.util.Arrays.stream;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Slf4j
public class CustomAuthorizationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getServletPath().equals("/login")) {
            filterChain.doFilter(request, response);

        }else {
            String authHeader = request.getHeader(AUTHORIZATION);
            if(authHeader != null && authHeader.startsWith("Valid ")){
                try {
                    String token = authHeader.substring("Valid ".length());

                    Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                    System.out.println(algorithm);

                    JWTVerifier verifier = JWT.require(algorithm).build();
                    System.out.println(verifier);

                    DecodedJWT decodedJWT = verifier.verify(token);
                    System.out.println(decodedJWT);

                    // Verify the Token of The Valid User
                    String username = decodedJWT.getSubject();

                    // Get the Roles of the User
                    String[] roles = decodedJWT.getClaim("roles").asArray(String.class);

                    Collection<SimpleGrantedAuthority> sga = new ArrayList<>();

                    stream(roles).forEach(role -> {
                        sga.add(new SimpleGrantedAuthority(role));
                    });

                    // Set the Authentication
                    // Here we are setting the Authentication
                    // Telling the Spring Security that this is the Authentication Credentials
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, null, sga);
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                    // Go for the Next Filter
                    filterChain.doFilter(request, response);


                }catch (Exception e){

                    log.error("Error logging in: {}", e.getMessage());
                    response.setHeader(HttpStatus.NOT_ACCEPTABLE.toString(), e.getMessage());
                    response.setStatus(HttpStatus.FORBIDDEN.value());
//                    response.sendError(HttpStatus.NOT_ACCEPTABLE.value());
                    Map<String, String> error = new HashMap<>();
                    error.put("error_message", e.getMessage());
                    response.setContentType("application/json");
                    new ObjectMapper().writeValue(response.getOutputStream(), error);

                }

            }else {
                filterChain.doFilter(request, response);
            }
        }
    }
}