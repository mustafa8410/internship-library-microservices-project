package com.example.webapi.security;

import com.example.webapi.entity.User;
import com.example.webapi.exceptions.security.JwtTokenExpiredException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.time.Instant;
import java.util.Base64;

@Component
public class JwtTokenProvider{

    @Value("${secret.key}")
    String secret;

    @Value("${token.expires.in}")
    Long durationTime;

    public String generateTokenWithUser(User user) {
        return Jwts.builder().setSubject(user.getUsername())
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plusSeconds(durationTime)))
                .signWith(generateKey())
                .compact();
    }

    private SecretKey generateKey(){
        byte[] decodedKey = Base64.getDecoder().decode(secret);
        return Keys.hmacShaKeyFor(decodedKey);
    }

    private Claims getPayload(String jwtToken){
        try{
            return Jwts.parser().setSigningKey(generateKey()).parseClaimsJws(jwtToken).getBody();
        }
        catch (ExpiredJwtException e){
            throw new JwtTokenExpiredException("expired token");
        }
    }

    public String extractUsername(String jwtToken){
        return getPayload(jwtToken).getSubject();
    }

    public boolean verifyDate(String jwtToken){
        try {
            getPayload(jwtToken);
        }
        catch (Exception e) {
            return false;
        }
        return true;
    }

}
