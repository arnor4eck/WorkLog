package com.arnor4eck.worklog.security.authorization.jwt;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtRequest {

    private final String email;

    private final String password;
}
