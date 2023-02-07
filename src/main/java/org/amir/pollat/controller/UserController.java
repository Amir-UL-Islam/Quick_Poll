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
@RequiredArgsConstructor
public class UserController {
    private final UserServices userServices;
//     For Everyone (new user)
//     Saving the user to DB
    @PostMapping("/save_new_user/")
    public ResponseEntity<?> Saving_The_New_User(@RequestBody Users user){
        URI location = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().path("/api/users/jwt/save").toUriString());
        userServices.save_user(user);
        return ResponseEntity.created(location).body(HttpStatus.CREATED);

    }

    @GetMapping("/get_all_user/")
    public ResponseEntity<List<Users>> getAllUser(){
        return ResponseEntity.ok().body(userServices.get_all_user());
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
//
//    private Users getLogInUser(Principal principal){
//        return usersRepository.findByUsername(principal.getName()).get();
//    }
//
//
//    //    List of roles of the loggedIn user
//    private List<String> getRoleOfTheLoggedInUser(Principal principal){
////        String rolls = getLogInUser(principal).getRoll();
//        String rolls = getLogInUser(principal).getRoll().toString();
//        List<String> assignedRolls = Arrays.stream(rolls.split(",")).collect(Collectors.toList());
//        if (assignedRolls.contains("ADMIN")){
//            return Arrays.stream(ADMIN_ROLE).collect(Collectors.toList());
//        }
//        if (assignedRolls.contains("MODERATOR")){
//            return Arrays.stream(MODERATOR_ROLE).collect(Collectors.toList());
//        }
//        if (assignedRolls.contains("USER")){
//            return Arrays.stream(DEFAULT_ROLE).collect(Collectors.toList());
//        }
//        return null;
//    }
//
//    // Assigning roles to the user
//    @GetMapping("/access/{userID}/{userRoll}")
//    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('MODERATOR')")
//    public String giveAccessTo(@PathVariable Long userID, @PathVariable String userRoll, Principal principal){
//        Users user = usersRepository.findById(userID).get();
//
//        // Getting the role of the loggedIn user, Who will assign the role
//        List<String> activeRolls = getRoleOfTheLoggedInUser(principal);
//
//        // Checking if the loggedIn user has the role to assign the role
//        if(activeRolls.contains(userRoll)){
//
////            String newRoll = user.getRoll().toString().concat("," + userRoll);
////            String newRoll = user.getRoll().concat("," + userRoll);
//            user.setRoll(Rolls.MODERATOR);
//        }
//        usersRepository.save(user);
//        return "Access Granted";
//    }


//    @GetMapping("/all_users/")
////    @PreAuthorize("hasAuthority('ADMIN')")
//    public ResponseEntity<List<Users>> loadAllUsers(){
//        return ResponseEntity.status(HttpStatus.OK).body(usersRepository.findAll());
//    }

//    @GetMapping("/randomUser")
//    @PreAuthorize("hasAuthority('USER')")
//    public String randomUser(){
//        return "You are a user";
//    }
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
