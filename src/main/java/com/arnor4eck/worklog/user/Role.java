package com.arnor4eck.worklog.user;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

/** Класс кастомных ролей
 * */
@Data
@Entity
@Table(name = "role")
@NoArgsConstructor
public class Role implements GrantedAuthority {

    /**
     * Уникальный идентификатор роли. База данных генерирует автоматически при сохранении объекта.
     * */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Название роли
     * */
    private String name;

    /**
     * Основной конструктор.
     *
     * @param _name строка, обозначающая название роли.
     * */
    public Role(String _name){
        this.name = _name;
    }

    /**
     * Переопределенный метод, возвращающий полномочия, в нашем случае - название роли.
     *
     * @return Строка - название роли.
     * */
    @Override
    public String getAuthority() {
        return this.name;
    }
}
