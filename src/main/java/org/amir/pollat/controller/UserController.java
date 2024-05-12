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
@AllArgsConstructor
public class UserController {
    private UserServices userServices;
    private UsersRepository usersRepository;

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
        userServices.reFreshToken(request, response);
    }
}
