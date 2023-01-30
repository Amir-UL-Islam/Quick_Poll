//package org.amir.pollat.security;
//
//import org.amir.pollat.entity.Users;
//import org.amir.pollat.repository.UsersRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import javax.inject.Inject;
//import java.util.Optional;
//
//@Service
//// Part of The Authentication Process
//public class Quick_PollUserDetailsService implements UserDetailsService{
//    @Autowired
//    private UsersRepository usersRepository;
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//
//        // Authentication Process Start Here
//        // But the problem Arise, Spring Don't Know How to Load the User
//        // It will Search for UserDetails
//        // So, We have to Implement UserDetails Interface
//        // Which is Implemented in Quick_PollUserDetails.java
//        Optional<Users> user = usersRepository.findByUsername(username);
//
//        user.orElseThrow(()-> new UsernameNotFoundException("User Not Found"));
//        return user.map(Quick_PollUserDetails::new).get();
////                .orElse(new User("amir", "amir", new ArrayList<>())
////                .orElseThrow(()-> new UsernameNotFoundException("User Not Found"));
//
//    }
//}
