package com.arnor4eck.worklog.security.authorization.jwt.jwt_utils;

/** Интерфейс для утилит JWT
 * */
public interface JwtUtils <T> {
    /** Генерация токена
     * */
    String generateToken(T object);
    /** Проверка валидности токена
     * */
    boolean validate(String token);
}
