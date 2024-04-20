package org.amir.pollat.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;
import org.amir.pollat.model.dtos.RoleToUser;
import org.amir.pollat.model.entity.Roles;
import org.amir.pollat.model.entity.Users;
import org.amir.pollat.repository.UsersRepository;
import org.amir.pollat.services.UserServices;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
@RequestMapping("/api/user")
//@RequiredArgsConstructor
@AllArgsConstructor
public class UserController {
    private UserServices userServices;
    private UsersRepository usersRepository;

    //     For Everyone (new user)
//     Saving the user to DB
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody Users user) {
        URI location = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().path("/api/user/create").toUriString());
        userServices.saveUser(user);
        return ResponseEntity.created(location).body(HttpStatus.CREATED);

    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllUser() {
        return ResponseEntity.ok().body(usersRepository.findAll());
    }

    @PostMapping("/add-role")
    public ResponseEntity<?> saveUserRole(@RequestBody Roles role) {
        return ResponseEntity.ok().body(userServices.saveRole(role));
    }

    @PostMapping("/set-user-role")
    public ResponseEntity<?> setRoleToUser(@RequestBody RoleToUser roleToUser) {
        userServices.addRoleToUser(roleToUser.getUsername(), roleToUser.getRole());
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @GetMapping("/refresh-token")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authHeader = request.getHeader(AUTHORIZATION);
        if (authHeader != null && authHeader.startsWith("Valid ")) { // startWith "Bearer"
            try {
                String refreshToken = authHeader.substring("Valid ".length());
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes()); // Same Secret Key as in CustomAuthenticationFilter for Signing the Token
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refreshToken);
                String username = decodedJWT.getSubject();
                Users user = userServices.findByUsername(username);


                // Creating New Access Token
                String accessToken = JWT.create()
                        .withSubject(user.toString())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 3 * 60 * 1000)) // For 2 minutes

                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles", user.getRoles().stream().map(Roles::getName).collect(Collectors.toList()))
                        .sign(algorithm);
                response.setHeader("access_token", accessToken);
                response.setHeader("refresh_token", refreshToken);


                // Setting the Tokens in the Body
                HashMap<String, String> tokens = new HashMap<>();
                tokens.put("access_token", accessToken);
                tokens.put("access_token_decode", JWT.decode(accessToken).getSubject());

                tokens.put("refresh_token", refreshToken);
                tokens.put("refresh_token_decode", JWT.decode(refreshToken).getSubject());

                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);

            } catch (Exception e) {

                response.setHeader(HttpStatus.NOT_ACCEPTABLE.toString(), e.getMessage());
                response.setStatus(HttpStatus.FORBIDDEN.value());
                Map<String, String> error = new HashMap<>();
                error.put("error_message", e.getMessage());
                response.setContentType("application/json");
                new ObjectMapper().writeValue(response.getOutputStream(), error);

            }

        } else {
            throw new RuntimeException("Refresh Token Not Found");
        }
    }
}
