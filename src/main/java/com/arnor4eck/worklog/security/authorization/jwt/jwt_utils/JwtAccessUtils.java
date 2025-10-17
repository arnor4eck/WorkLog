package com.arnor4eck.worklog.security.authorization.jwt.jwt_utils;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;

/** Утилита для JWT QR-кода
 * */
@Component
public class JwtAccessUtils implements JwtUtils<String>{
    /** Секрет пользователя
     * */
    @Value("${jwt.secret}")
    private String secret;

    /** Время жизни токена
     * */
    private Long lifetime = 300000L;

    /** @see JwtUtils#generateToken(Object) 
     * */
    @Override
    public String generateToken(String object) {
        Date issued = new Date();
        Date expired = new Date(issued.getTime() + lifetime);
        return Jwts.builder()
                .subject(object)
                .issuedAt(issued) // создание
                .expiration(expired) // истечение
                .signWith(Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8))) // секрет
                .compact();
    }

    /** @see JwtUtils#validate(String) 
     * */
    @Override
    public boolean validate(String token) {
        try {
            Jwts.parser()
                    .verifyWith(Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)))
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}
