package org.amir.pollat.controller;

import lombok.*;
import org.amir.pollat.model.dtos.RoleToUser;
import org.amir.pollat.model.entity.Roles;
import org.amir.pollat.model.entity.Users;
import org.amir.pollat.repository.UsersRepository;
import org.amir.pollat.services.UserServices;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

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

    @PostMapping("/set-user-role/")
    public ResponseEntity<?> setRoleToUser(@RequestBody RoleToUser roleToUser) {
        userServices.addRoleToUser(roleToUser.getUsername(), roleToUser.getRole());
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
