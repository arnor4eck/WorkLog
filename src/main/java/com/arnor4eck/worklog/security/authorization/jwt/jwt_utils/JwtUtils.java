package com.arnor4eck.worklog.security.authorization.jwt.jwt_utils;

public interface JwtUtils <T> {
    String generateToken(T object);
    boolean validate(String token);
}
