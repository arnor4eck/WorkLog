package com.arnor4eck.worklog.user.utils;

import com.arnor4eck.worklog.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/** Data-Transfer Object для User
 * @see User
 * */
@Data
@Builder
@AllArgsConstructor
public class UserDTO {

    /** Имя пользователя в формате ФИО
     * */
    protected final String username;

    /** Должность пользователя
     * */
    protected final String position;

    /** Электронная почта пользователя
     * */
    protected final String email;


    /** Создание DTO из класса {@link User}
     * @param user {@link User}
     * @return {@link UserDTO}
     * */
    public static UserDTO formUser(User user){
        return UserDTO.builder()
                .username(user.getUsername())
                .position(user.getPosition())
                .email(user.getEmail())
                .build();
    }
}
