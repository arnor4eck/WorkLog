package com.arnor4eck.worklog.user;

import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/** Сервис для работы с пользователем
 * */
@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    /** Возвращает текущего авторизированного пользователя
     * @return User
     * */
    public User getAuthedUser(){
        return userRepository.findByEmail((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }
}
