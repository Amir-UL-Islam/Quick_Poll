package org.amir.pollat.security.jwtfilters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;

    //   Authentication is Here
    //   This method is called when the user tries to authenticate
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        log.info("Inside attemptAuthentication");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        log.info("Username is: {} & Password is : {}", username, password);
        //        Login/Authentication is Here
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        return authenticationManager.authenticate(authenticationToken);
    }

    //    Access Token Refresh Token
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        User user = (User) authResult.getPrincipal();
        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());

        // Creating New Access Token
        String accessToken = JWT.create()

//                Creating Token with
//                .withSubject(user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()).toString())
                .withSubject(user.toString())
//                 Token Time
                .withExpiresAt(new Date(System.currentTimeMillis() + 3 * 60 * 1000)) // For 2 minutes

                .withIssuer(request.getRequestURL().toString())

                // Setting the Claims and Authorities
                .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);


        // Refresh Token
        String refreshToken = JWT.create()

//                Creating Token with
                .withSubject(user.toString())

//                 Token Time
                .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000)) // For 5 minutes
                .withIssuer(request.getRequestURL().toString())

//                // Setting the Claims and Authorities
//                .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);

//        In the Header
        response.setHeader("access_token", accessToken);
        response.setHeader("refresh_token", refreshToken);


//        Let's say we want our token to be in the body
//        Way 01

//        response.setContentType("application/json");
//        response.setCharacterEncoding("UTF-8");
//        response.getWriter().write("access_token: " + access_token + "\n" + " refresh_token: " + refresh_token);

//        Way 02
        HashMap<String, String> tokens = new HashMap<>();
        tokens.put("access_token", accessToken);
        tokens.put("access_token_decode", JWT.decode(accessToken).getSubject());

        tokens.put("refresh_token", refreshToken);
        tokens.put("refresh_token_decode", JWT.decode(refreshToken).getSubject());

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        new ObjectMapper().writeValue(response.getOutputStream(), tokens);

//        Also we want to put the tokens into the cookies'
//        response.addHeader("Set-Cookie", "access_token=" + access_token + "; HttpOnly; Path=/; Max-Age=120");
//        response.addHeader("Set-Cookie", "refresh_token=" + refresh_token + "; HttpOnly; Path=/; Max-Age=300");


    }
}
