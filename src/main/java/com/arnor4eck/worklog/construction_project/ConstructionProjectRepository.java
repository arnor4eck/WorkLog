package com.arnor4eck.worklog.construction_project;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/** Репозиторий для полигонов
 * @see ConstructionProject
 * */
@Repository
public interface ConstructionProjectRepository extends CrudRepository<ConstructionProject, Long> {
    /** Находит все полигоны, к которым прикреплен пользователь
     * @param userId ID пользователя
     * @return Список полигонов
     * */
    List<ConstructionProject> findByUsersId(Long userId);

    /** Проверяет, существует ли полигон по его имени
     * @param name Название полигона
     * */
    boolean existsByName(String name);

    /** Проверяет, прикреплен ли пользователь к данному полигону
     * @param projectId ID полигона
     * @param email Электронная почта пользователя
     * */
    @Query(value = "SELECT EXISTS(" +
            "SELECT 1 FROM project_users pu " +
            "JOIN users u ON u.id = pu.user_id " +
            "WHERE pu.project_id = :project_id AND u.email = :email)", nativeQuery = true)
    boolean projectContainsUser(@Param("project_id") Long projectId,
                                @Param("email") String email);
}
