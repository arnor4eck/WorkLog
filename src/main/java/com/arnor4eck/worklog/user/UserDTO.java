package com.arnor4eck.worklog.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
public class UserDTO {

    private final String username;

    private final String position;

    private final String email;

    public static UserDTO formUser(User user){
        return UserDTO.builder()
                .username(user.getUsername())
                .position(user.getPosition())
                .email(user.getEmail())
                .build();
    }
}
