package org.amir.pollat.security;

import org.amir.pollat.entity.Users;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

public class Quick_PollUserDetails implements UserDetails{
    public String username;
    public String password;
    public Collection<GrantedAuthority> authorities;
    public boolean isActive;

    public Quick_PollUserDetails(Users users){
        this.username = users.getUsername();
        this.password = users.getPassword();
        this.authorities = Arrays.stream(users.getRoll().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        this.isActive = users.isActive();
    }



    // Now the UserDetails will use the Users Entity to Load the User
    // Like I Said the Spring Security will Search for UserDetails
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isActive;
    }
}
