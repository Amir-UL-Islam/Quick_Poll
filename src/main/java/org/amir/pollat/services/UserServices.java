package org.amir.pollat.services;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.amir.pollat.entity.Roles;
import org.amir.pollat.entity.Users;
import org.amir.pollat.repository.RoleRepository;
import org.amir.pollat.repository.UsersRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.userdetails.User;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserServices implements UserDetailsService {
    private final UsersRepository usersRepository;
    private final RoleRepository roleRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = usersRepository.findByUsername(username).get();
        if (user == null) {
            log.info("User not found");
            throw new UsernameNotFoundException("User not found");
        }else {
            log.info("User found");
        }

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();

        user.getRoles().forEach(roles -> authorities.add(new SimpleGrantedAuthority(roles.getName())));

        return new User(
                user.getUsername(),
                user.getPassword(),
                authorities
        );
    }
    public Users save_user(Users user){
        log.info("Saving user to DB");
        usersRepository.save(user);
        return user;
    }

    public Roles save_role(Roles role){
        log.info("Saving role to DB");
        roleRepository.save(role);
        return role;
    }

    public void add_role_to_user(String username, String role){
        log.info("Adding role to user");
        Users user = usersRepository.findByUsername(username).get();
        Optional<Roles> roles = roleRepository.findByName(role);
        user.getRoles().add(roles.get());
    }

    public List<Users> get_all_user(){
        log.info("Getting user from DB");
        return usersRepository.findAll();
    }

}
