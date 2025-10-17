package com.arnor4eck.worklog.construction_project;

import com.arnor4eck.worklog.construction_project.coordinates.Coordinates;
import com.arnor4eck.worklog.construction_project.utils.ObjectStatus;
import com.arnor4eck.worklog.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

/** Сущность строительного полигона
 * */
@Entity
@Table(name = "construction_projects")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConstructionProject {
    /** Уникальный идентификатор полигона. База данных генерирует автоматически при сохранении объекта.
     * */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Название полигона
     * */
    @Column(name = "name", unique = true, nullable = false)
    private String name;

    /** Описание полигона
     * */
    @Column(nullable = false, length = 1024)
    private String description;

    /** Координаты, связанные с полигоном
     * @see Coordinates
     * */
    @ElementCollection
    @CollectionTable(name = "project_points", joinColumns = @JoinColumn(name = "project_id"))
    @OrderColumn(name = "point_order")
    private List<Coordinates> coordinates;

    public ConstructionProject(String name, String description){
        this.name = name;
        this.description = description;
        users = new HashSet<>();
    }

    /** Ответственный пользователь, назначаемый от подрядчика
     * @see User
     * */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contractor_id")
    private User responsibleContractor;

    public void addResponsibleContractor(User user){
        this.responsibleContractor = user;
    }

    /** Ответственный пользователь, назначаемый от технического надзора
     * @see User
     * */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supervision_id")
    private User responsibleSupervision;

    public void addResponsibleSupervision(User user){
        this.responsibleSupervision = user;
    }

    /** Статус объекта
     * @see ObjectStatus
     * */
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ObjectStatus status;

    /** Все пользователи, прикрепленные к объекту
     * @see User
     * */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "project_users",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    @Builder.Default
    private Set<User> users = new HashSet<>();
}
