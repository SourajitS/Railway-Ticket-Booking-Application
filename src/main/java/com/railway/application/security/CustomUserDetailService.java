package com.railway.application.security;

import com.railway.application.entity.User;
import com.railway.application.exceptions.ResourceNotFoundException;
import com.railway.application.repository.UserRepository;
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

    private final UserRepository userRepository;

    public CustomUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {


        User user = userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found with username " + username));

        CustomUserDetail customUserDetail = new CustomUserDetail(user);
//        UserDetails userDetails = User
//                .builder()
//                .username("user")
//                .password("{noop}user123")
//                .roles("USER")
//                .build();
//
//        if(userDetails.getUsername().equals(username))
//        {
//            return userDetails;
//        }
//        else {
//            throw new UsernameNotFoundException("User not found with username "+username);
//        }
        return customUserDetail;

    }
}
