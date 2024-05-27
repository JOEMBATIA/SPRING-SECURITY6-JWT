package com.joe.springsec6demo.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.security.Key;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

@Service
@Slf4j
public class JwtService {
    // This generates a secure random key for signing the JWT tokens using the HS256 algorithm
    private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // This method takes a UserDetails object as a parameter, which represents
    // the authenticated user. It then generates a JWT token with the following claims
    public String generateToken(UserDetails user){
        return Jwts.builder()
                // sets the subject with the username of our entity
                .setSubject(user.getUsername())
                //sets the issued at timestamp of the current time
                .setIssuedAt(new Date(System.currentTimeMillis()))
                // adds a custom claim named authorities with the user;s authorities(roles)
                //as a comma-separated string
                .claim("authorities", populateAuthorities(user.getAuthorities()))
                // sets the expiration of this token to 24hrs
                .setExpiration(new Date(System.currentTimeMillis() + 86400000))
                //signs the token using the SECRET_KEY and the HS256 ALG
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
                // compact the token into a URL-safe string representation
                .compact();
    }

    public String populateAuthorities(Collection<? extends GrantedAuthority> authorities){
        Set<String> authoritiesSet = new HashSet<>();
        for (GrantedAuthority authority: authorities){
            authoritiesSet.add(authority.getAuthority());
        }

        return String.join(",", authoritiesSet);
    }

    public Claims getClaims(String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String getUserName(String token){
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getClaims(token);
        return claimsResolver.apply(claims);
    }

    public Boolean isValidToken(String token, UserDetails userDetails){
        final String userName = getUserName(token);
        return (userName.equals(userDetails.getUsername()));
    }

}
