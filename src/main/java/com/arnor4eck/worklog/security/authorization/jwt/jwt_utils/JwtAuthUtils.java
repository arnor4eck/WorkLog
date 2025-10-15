package com.arnor4eck.worklog.security.authorization.jwt.jwt_utils;

import com.arnor4eck.worklog.user.User;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import io.jsonwebtoken.Jwts;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.*;

/** Утилита для JWT
 * */
@Component
public class JwtAuthUtils implements JwtUtils<User>{
    /** Секрет пользователя
     * */
    @Value("${jwt.secret}")
    private String secret;

    /** Время жизни токена
     * */
    @Value("${jwt.lifetime}")
    private Long lifetime;

    /** Генерация токена
     * @param user Пользователь
     * @return Строка - токен
     * */
    @Override
    public String generateToken(User user){
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList());
        Date issued = new Date();
        Date expired = new Date(issued.getTime() + lifetime);
        Key key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));

        return Jwts.builder()
                .claims(claims) // полезная информация
                .subject(user.getEmail())
                .issuedAt(issued) // создание
                .expiration(expired) // истечение
                .signWith(key) // секрет
                .compact();
    }

    /** Проверка токена на валидность
     * @param token токен
     * @return true в случае валидности, иначе - false
     * */
    public boolean validate(String token){
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

    /** Получение email из токена
     * @param token токен
     * @return строка - email
     * */
    public String getEmail(String token){
        return getClaimsFromToken(token).getSubject();
    }

    /** Получение ролей пользователя из токена
     * @param token токен
     * @return список - роли пользователя
     * @see com.arnor4eck.worklog.user.Role
     * */
    public List<String> getRoles(String token){
        return getClaimsFromToken(token).get("roles", List.class);
    }

    /** Получение содержимого токена
     * @param token токен
     * @return Claims - содержимое
     * */
    private Claims getClaimsFromToken(String token){
        return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)))
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
