package com.arnor4eck.worklog.user.utils;

import com.arnor4eck.worklog.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class UserInfo {

    private final Long id;

    private final String role;

    private final String username;

    protected final String position;

    public static UserInfo fromUser(User user){
        return UserInfo.builder()
                .id(user.getId())
                .position(user.getPosition())
                .username(user.getUsername())
                .role(user.getAuthorities().get(0).getName())
                .build();
    }
}
