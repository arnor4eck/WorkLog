package com.arnor4eck.worklog.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/** Data-Transfer Object для User
 * @see User
 * */
@Data
@Builder
public class UserDTO {

    /** Имя пользователя в формате ФИО
     * */
    private final String username;

    /** Должность пользователя
     * */
    private final String position;

    /** Электронная почта пользователя
     * */
    private final String email;

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
