package com.arnor4eck.worklog.user;

import com.arnor4eck.worklog.user.utils.UserInfo;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** Контроллер пользователя
 * */
@RestController
@RequestMapping("user/")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    /** @see UserInfo
     * */
    @GetMapping
    public UserInfo getUserInfo(){
        return UserInfo.fromUser(userService.getAuthedUser());
    }
}
