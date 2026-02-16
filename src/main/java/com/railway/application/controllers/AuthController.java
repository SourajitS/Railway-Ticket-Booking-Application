package com.railway.application.controllers;

import com.railway.application.dto.ErrorResponse;
import com.railway.application.dto.JwtResponse;
import com.railway.application.dto.LoginRequest;
import com.railway.application.dto.UserDto;
import com.railway.application.entity.User;
import com.railway.application.repository.UserRepository;
import com.railway.application.security.JwtHelper;
import com.railway.application.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private AuthenticationManager authenticationManager;
    private UserDetailsService userDetailsService;
    private JwtHelper jwtHelper;
    private UserService userService;
    private UserRepository userRepository;
    private ModelMapper modelMapper;

    public AuthController(AuthenticationManager authenticationManager, UserDetailsService userDetailsService, JwtHelper jwtHelper, UserService userService, UserRepository userRepository, ModelMapper modelMapper) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtHelper = jwtHelper;
        this.userService = userService;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {

        ErrorResponse  errorResponse;
        try {
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password());
            this.authenticationManager.authenticate(authenticationToken);

            UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.username());
            String token = jwtHelper.generateToken(userDetails);
            User user = userRepository.findByEmail(loginRequest.username()).get();

            JwtResponse jwtResponse = new JwtResponse(token,modelMapper.map(user,UserDto.class));
            return new ResponseEntity<>(jwtResponse, HttpStatus.OK);
        }
        catch (BadCredentialsException ex) {
            System.out.println("Invalid Credentials");
             errorResponse = new ErrorResponse(
                    "Invalid Credentials",
                    "403",
                    false);

        }



        return new ResponseEntity<>(errorResponse,HttpStatus.BAD_REQUEST);
    }
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserDto userDto)

    {
        UserDto dto = userService.registerUser(userDto);
        return new ResponseEntity<>(dto,HttpStatus.CREATED);
    }

}
