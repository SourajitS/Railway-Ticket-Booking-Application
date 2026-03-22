package com.railway.application.dto;

public record JwtResponse( String token,String refreshToken,UserDto userDto ) {
}
