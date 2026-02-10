package com.railway.application.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {
//  private   PasswordEncoder passwordEncoder;
//
//    public CustomUserDetailService(PasswordEncoder passwordEncoder) {
//        this.passwordEncoder = passwordEncoder;
//    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {


        UserDetails userDetails = User
                .builder()
                .username("user")
                .password("{noop}user123")
                .roles("USER")
                .build();

        if(userDetails.getUsername().equals(username))
        {
            return userDetails;
        }
        else {
            throw new UsernameNotFoundException("User not found with username "+username);
        }

    }
}
