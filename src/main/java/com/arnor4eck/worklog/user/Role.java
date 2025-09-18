package com.arnor4eck.worklog.user;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@Data
@Entity
@Table(name = "role")
@NoArgsConstructor
/**
 * Класс кастомных ролей
 * */
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    public Role(String _name){
        this.name = _name;
    }

    @Override
    public String getAuthority() {
        return this.name;
    }
}
