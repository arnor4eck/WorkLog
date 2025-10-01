package com.arnor4eck.worklog.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/** Репозиторий для кастомных ролей
 * */
@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {}
