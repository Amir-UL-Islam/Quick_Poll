package org.amir.pollat.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.amir.pollat.model.entity.Roles;
import org.amir.pollat.model.entity.Users;
import org.amir.pollat.repository.RoleRepository;
import org.amir.pollat.repository.UsersRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.userdetails.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;


@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserServices implements UserDetailsService {
    private final UsersRepository usersRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    // UserDetailsService is an interface that can be implemented to provide the loadUserByUsername method
    // This method is used by Spring Security to load the user from the database
    // And Will for Authentication
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Users> user = usersRepository.findByUsername(username);
        if (user.isEmpty()) {
            log.info("User not found");
            throw new UsernameNotFoundException("User not found");
        } else {
            log.info("User found");
        }

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();

        user.get().getRoles().forEach(roles -> authorities.add(new SimpleGrantedAuthority(roles.getName())));
        log.info("Authorities are: {}", authorities);

        return new User(
                user.get().getUsername(),
                user.get().getPassword(),
                authorities
        );
    }

    public void saveUser(Users user) {
        log.info("Saving user to DB");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        usersRepository.save(user);
    }

    public Roles saveRole(Roles role) {
        log.info("Saving role to DB");
        roleRepository.save(role);
        return role;
    }

    public void addRoleToUser(String username, String role) {
        log.info("Adding role to user");
        Users user = usersRepository.findByUsername(username).get();
        Optional<Roles> roles = roleRepository.findByName(role);
        user.getRoles().add(roles.get());
    }

    public List<Users> getAllUser() {
        log.info("Getting user from DB");
        return usersRepository.findAll();
    }

    public Users findByUsername(String username) {
        return usersRepository.findByUsername(username).get();
    }

    public void reFreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authHeader = request.getHeader(AUTHORIZATION);
        if (authHeader != null && authHeader.startsWith("Valid ")) { // startWith "Bearer"
            try {
                String refreshToken = authHeader.substring("Valid ".length());
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes()); // Same Secret Key as in CustomAuthenticationFilter for Signing the Token
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refreshToken);
                String username = decodedJWT.getSubject();
                Users user = findByUsername(username);

                // Creating New Access Token
                String accessToken = JWT.create()
                        .withSubject(user.getUsername())
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
