package org.amir.pollat.controller;

import lombok.*;
import org.amir.pollat.entity.Roles;
import org.amir.pollat.entity.Users;
import org.amir.pollat.repository.UsersRepository;
import org.amir.pollat.services.UserServices;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/users/jwt/")
//@RequiredArgsConstructor
@AllArgsConstructor
public class UserController {
    private UserServices userServices;
    private  UsersRepository usersRepository;

    //     For Everyone (new user)
//     Saving the user to DB
    @PostMapping("/save_new_user/")
    public ResponseEntity<?> Saving_The_New_User(@RequestBody Users user){
        URI location = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().path("/api/users/jwt/save").toUriString());
        userServices.save_user(user);
        return ResponseEntity.created(location).body(HttpStatus.CREATED);

    }

    @GetMapping("/get_all_user/")
    public ResponseEntity<?> getAllUser(){
        return ResponseEntity.ok().body(usersRepository.findAll());
    }

    @PostMapping("/save_user_role/")
    public ResponseEntity<?> saveUserRole(@RequestBody Roles roles){
        return ResponseEntity.ok().body(userServices.save_role(roles));
    }

    @PostMapping("/set_role_to_user/")
    public ResponseEntity<?> setRoleToUser(@RequestBody SetRoleToUser setRoleToUser){
        userServices.add_role_to_user(setRoleToUser.getUsername(), setRoleToUser.getRole());
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
class SetRoleToUser{
    private String username;
    private String role;
}
