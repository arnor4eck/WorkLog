package com.arnor4eck.worklog.construction_project;

import com.arnor4eck.worklog.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Entity
@Table(name = "construction_projects")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConstructionProject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(name = "coordinate_x", nullable = false)
    private Double coordinateX;

    @Column(name = "coordinate_y", nullable = false)
    private Double coordinateY;

    public ConstructionProject(String name, String description,
                               double coordinateX, double coordinateY){
        this.name = name;
        this.description = description;
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
    }

    @OneToOne
    @JoinColumn(name = "contractor_id", referencedColumnName = "id")
    private User responsibleContractor;

    public void addResponsibleContractor(User user){
        this.responsibleContractor = user;
        users.add(user);
    }


    @OneToOne
    @JoinColumn(name = "supervision_id", referencedColumnName = "id")
    private User responsibleSupervision;

    public void addResponsibleSupervision(User user){
        this.responsibleSupervision = user;
        users.add(user);
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "project_users",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> users = new HashSet<>();
}
