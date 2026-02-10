package com.railway.application.controllers;

import com.railway.application.dto.ErrorResponse;
import com.railway.application.dto.JwtResponse;
import com.railway.application.dto.LoginRequest;
import com.railway.application.security.JwtHelper;
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

@RestController
@RequestMapping("/auth")
public class AuthController {

    private AuthenticationManager authenticationManager;
    private UserDetailsService userDetailsService;
    private JwtHelper jwtHelper;

    public AuthController(AuthenticationManager authenticationManager, UserDetailsService userDetailsService, JwtHelper jwtHelper) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtHelper = jwtHelper;
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

            JwtResponse jwtResponse = new JwtResponse(token, userDetails.getUsername());
            return new ResponseEntity<>(jwtResponse, HttpStatus.OK);
        }
        catch (BadCredentialsException ex) {
            System.out.println("Invalid Credentials");
             errorResponse = new ErrorResponse(
                    "Invalid Credentials",
                    "403",
                    false);

        }



        return new ResponseEntity<>(errorResponse,HttpStatus.FORBIDDEN);
    }

}
