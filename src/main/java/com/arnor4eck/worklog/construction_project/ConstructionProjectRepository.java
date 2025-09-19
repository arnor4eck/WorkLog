package com.arnor4eck.worklog.construction_project;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConstructionProjectRepository extends CrudRepository<ConstructionProject, Long> {}
