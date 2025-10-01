package com.arnor4eck.worklog.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/** Пользователь, хранящийся в базе данных
 * */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User implements UserDetails {

    /** Уникальный идентификатор пользователя. База данных генерирует автоматически при сохранении объекта.
     * */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Электронная почта пользователя
     * */
    private String email;

    /** Должность пользователя
     * */
    private String position;

    /** Пароль пользователя.
     * @see com.arnor4eck.worklog.security.SecurityConfig#passwordEncoder()
     * */
    private String password;

    /** Имя пользователя в формате ФИО
     * */
    private String username;

    /** Конструктор со всеми параметрами
     * */
    public User(String email, String position,
                String password, String username,
                List<Role> authorities){
        this.email = email;
        this.position = position;
        this.password = password;
        this.username = username;
        this.authorities = authorities;
    }

    /** Список ролей пользователя
     * @see Role
     * */
    @ManyToMany
    @JoinTable(
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> authorities;

    /** Переопределённый метод получения ролей пользователя
     * @return пользовательские роли
     * */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }


    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
