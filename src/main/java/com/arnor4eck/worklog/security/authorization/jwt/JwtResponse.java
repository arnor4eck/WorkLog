package com.arnor4eck.worklog.security.authorization.jwt;

import lombok.AllArgsConstructor;
import lombok.Data;

/** Ответ на запрос токена клиенту
 * */
@Data
@AllArgsConstructor
public class JwtResponse {
    /** Токен
     * */
    private String token;
}
