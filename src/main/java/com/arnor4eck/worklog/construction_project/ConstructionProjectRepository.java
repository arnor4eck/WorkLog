package com.arnor4eck.worklog.construction_project;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConstructionProjectRepository extends CrudRepository<ConstructionProject, Long> {
    List<ConstructionProject> findByUsersId(Long userId);
    boolean existsByName(String name);

    @Query(value = "SELECT EXISTS(" +
            "SELECT 1 FROM project_users pu " +
            "JOIN users u ON u.id = pu.user_id " +
            "WHERE pu.project_id = :project_id AND u.email = :email)", nativeQuery = true)
    boolean projectContainsUser(@Param("project_id") Long projectId,
                                @Param("email") String email);
}
