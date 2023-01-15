package org.amir.pollat.Security;

import org.amir.pollat.entity.Users;
import org.amir.pollat.repository.UsersRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.ArrayList;

@Component
public abstract class Quick_PollUserDetailsService implements UserDetailsService {
    @Inject
    private UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users users =  usersRepository.findByUsername(username);
        if (users == null) {
            throw new UsernameNotFoundException(String.format("User with the Username %s Dose not found", username));
        }
        // Create a granted authority based on user's role.
// Can't pass null authorities to user. Hence initialize with an empty arraylist
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(users.getUsername(), users.getPassword(), new ArrayList<>());
        return userDetails;
    }
}
