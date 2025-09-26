package com.arnor4eck.worklog.construction_project;

import com.arnor4eck.worklog.construction_project.coordinates.Coordinates;
import com.arnor4eck.worklog.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Entity
@Table(name = "construction_projects")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConstructionProject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @ElementCollection
    @CollectionTable(name = "project_points", joinColumns = @JoinColumn(name = "project_id"))
    @OrderColumn(name = "point_order")
    private List<Coordinates> coordinates;

    public ConstructionProject(String name, String description){
        this.name = name;
        this.description = description;
        users = new HashSet<>();
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
    @Builder.Default
    private Set<User> users = new HashSet<>();
}
