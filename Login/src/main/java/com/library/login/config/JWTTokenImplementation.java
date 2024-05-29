package com.library.login.config;

import com.library.login.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service("jwtTokenImplementation")
public class JWTTokenImplementation implements JWTTokenConfiguration {

    @Value("${jwt.secret}")
    private String secret;

    @Override
    public Map<String, String> generateToken(User user) {
        // decode the secret and make new Key
        Key newKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
        // creating the jwt token
        String jwtToken = Jwts.builder().subject("Username: " + user.getUsername() + ", Roles: " + user.getRoles() + ", Login: Success").issuedAt(new Date()).signWith(newKey).compact();
        Map<String, String> jwtTokenMap = new HashMap<>();
        // add the created key to map and some additional data to check
        jwtTokenMap.put("token", jwtToken);
        jwtTokenMap.put("message", "Login Successful");
        return jwtTokenMap;
    }
}
