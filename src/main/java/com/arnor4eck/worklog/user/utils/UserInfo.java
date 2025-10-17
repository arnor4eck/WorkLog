package com.arnor4eck.worklog.user.utils;

import com.arnor4eck.worklog.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/** Информация о пользователе
 * */
@AllArgsConstructor
@Data
@Builder
public class UserInfo {

    /** Уникальный идентификатор - хранится в БД
     * @see User
     * */
    private final Long id;

    /** Роль пользователя
     * @see com.arnor4eck.worklog.user.role.Role
     * */
    private final String role;

    /** ФИО пользователя
     * @see User
     * */
    private final String username;

    /** Должность пользователя
     * @see User
     * */
    protected final String position;

    /** Создание UserInfo из класса {@link User}
     * @param user {@link User}
     * @return {@link UserInfo}
     * */
    public static UserInfo fromUser(User user){
        return UserInfo.builder()
                .id(user.getId())
                .position(user.getPosition())
                .username(user.getUsername())
                .role(user.getAuthorities().get(0).getName())
                .build();
    }
}
