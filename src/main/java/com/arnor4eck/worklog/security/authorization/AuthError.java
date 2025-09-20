package com.arnor4eck.worklog.security.authorization;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AuthError {
    private int status;
    private String message;
    private LocalDateTime timeStamp = LocalDateTime.now();

    public AuthError(int status, String message){
        this.status = status;
        this.message = message;
    }
}
