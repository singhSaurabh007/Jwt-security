package com.example.BackendMovieReview.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;



@Component
public class JwtGenerator {

    public String generateToken(Authentication authentication){
        String username = authentication.getName();
        Date currentDate = new Date();
        Date expiryDate = new Date(currentDate.getTime()+SecurityConstants.JWTexpiration);


        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS256,SecurityConstants.JwtSecret)
                .compact();

        return token;



    }


    public String getUsernameFromJwt(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(SecurityConstants.JwtSecret)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public boolean validateToken(String token){
        try{
            Jwts.parser().setSigningKey(SecurityConstants.JwtSecret).parseClaimsJws(token);
            return  true;
        }catch (Exception ex){
            throw new AuthenticationCredentialsNotFoundException("Jwt was expired or incorrect credentials");
        }
    }
}
