package org.amir.pollat.controller;

import org.amir.pollat.entity.Users;
import org.amir.pollat.repository.UsersRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users/")
public class UserController {
    public static final String[] DEFAULT_ROLE = {"USER"};
    public static final String[] ADMIN_ROLE = {"ADMIN", "USER"};
    private static final String[] MODERATOR_ROLE = {"MODERATOR"};
    @Inject
    private UsersRepository usersRepository;

    @Inject
    private PasswordEncoder passwordEncoder;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();

    }

    // For Everyone (new user)
    // Saving the user to DB
    @PostMapping("/newUser/")
    public String joinGroup(@RequestBody Users users){

        users.setRoll(Arrays.toString(DEFAULT_ROLE));
        String ePassword = passwordEncoder.encode(users.getPassword());
        users.setPassword(ePassword);
        usersRepository.save(users);
        return "User Created";
    }

    // Assigning roles to the user
    @GetMapping("/access/{userID}/{userRoll}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('MODERATOR')")
    public String giveAccessTo(@PathVariable Long userID, @PathVariable String userRoll, Principal principal){
        Users user = usersRepository.findById(userID).get();

        // Getting the role of the loggedIn user, Who will assign the role
        List<String> activeRolls = getRoleOfTheLoggedInUser(principal);

        // Checking if the loggedIn user has the role to assign the role
        if(activeRolls.contains(userRoll)){
            String newRoll = user.getRoll().concat("," + userRoll);
            user.setRoll(newRoll);
        }
        usersRepository.save(user);
        return "Access Granted";
    }


//    List of roles of the loggedIn user
    private List<String> getRoleOfTheLoggedInUser(Principal principal){
        String rolls = getLogInUser(principal).getRoll();
        List<String> assignedRolls = Arrays.stream(rolls.split(",")).collect(Collectors.toList());
        if (assignedRolls.contains("ADMIN")){
            return Arrays.stream(ADMIN_ROLE).collect(Collectors.toList());
        }
        if (assignedRolls.contains("MODERATOR")){
            return Arrays.stream(MODERATOR_ROLE).collect(Collectors.toList());
        }
        if (assignedRolls.contains("USER")){
            return Arrays.stream(DEFAULT_ROLE).collect(Collectors.toList());
        }
        return null;
    }

    private Users getLogInUser(Principal principal){
        return usersRepository.findByUsername(principal.getName()).get();
    }


    @GetMapping("/allUsers")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Users> loadAllUsers(){
        return usersRepository.findAll();
    }

    @GetMapping("/randomUser")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> randomUser(){
        return new ResponseEntity<>("Random User", HttpStatus.CONTINUE);
    }
}
