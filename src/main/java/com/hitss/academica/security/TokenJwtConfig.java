package com.hitss.academica.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;

@Component
public class TokenJwtConfig {
    public final SecretKey SECRET_KEY;
    public final long EXPIRATION_TIME;
    public static final String PREFIX_TOKEN = "Bearer ";
    public static final String HEADER_AUTHORIZATION = "Authorization";
    public static final String CONTENT_TYPE = "application/json";

    public TokenJwtConfig(@Value("${jwt.secret}") String secret,
                          @Value("${jwt.expiration.ms}") long expirationTime) {
        byte[] keyBytes = Base64.getDecoder().decode(secret);
        this.SECRET_KEY = Keys.hmacShaKeyFor(keyBytes);
        this.EXPIRATION_TIME = expirationTime;
    }
}