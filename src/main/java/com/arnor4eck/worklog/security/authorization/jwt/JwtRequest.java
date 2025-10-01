package com.arnor4eck.worklog.security.authorization.jwt;

import lombok.AllArgsConstructor;
import lombok.Data;

/** Запрос на получение токена
 * @see com.arnor4eck.worklog.user.User
 * */
@Data
@AllArgsConstructor
public class JwtRequest {
    /** Электронная почта
     * */
    private final String email;

    /** Пароль
     * */
    private final String password;
}
