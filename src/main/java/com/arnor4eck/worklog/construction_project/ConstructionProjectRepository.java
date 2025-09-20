package com.arnor4eck.worklog.construction_project;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConstructionProjectRepository extends CrudRepository<ConstructionProject, Long> {
    List<ConstructionProject> findByUsersId(Long userId);
}
