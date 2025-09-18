package com.arnor4eck.worklog.user;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

@Configuration
public class UserConfig {

    @Bean
    /*
    * Поиск пользователя по email
    * */
    public UserDetailsService userDetailsServiceEmail(UserRepository repo){
        return email -> Optional.ofNullable(repo.findByEmail(email))
                .orElseThrow(() -> new UsernameNotFoundException(
                        String.format("User with email %s does not exist.", email)));
    }
}
