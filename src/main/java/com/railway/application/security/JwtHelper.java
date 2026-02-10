package com.railway.application.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtHelper {

    private static final long JWT_VALIDITY=5*60*1000;
    private final String SECRET="my-super-secret-key-my-super-secret-key-aaaaaafgdgdgggggggggggggggggggggggggggggggggggggjh";
    private Key key;

    @PostConstruct
    public void init()
    {
        this.key= Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    public String generateToken(UserDetails userDetails)
    {
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+JWT_VALIDITY))
                .signWith(key,SignatureAlgorithm.HS256)
                .compact();
    }

    public String getUsernameFromToken(String token)
    {
        return  getClaims(token).getSubject();
    }

    //validate token

    public boolean isTokenValid(String token,UserDetails userDetails)
    {
        String username = getUsernameFromToken(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    //check token expiration
    private boolean isTokenExpired(String token) {
      return   getClaims(token).getExpiration().before(new Date());
    }

    private Claims getClaims(String token) {
     return Jwts.parser()
             .setSigningKey(key)
             .build()
             .parseClaimsJws(token)
             .getBody();

    }
}
