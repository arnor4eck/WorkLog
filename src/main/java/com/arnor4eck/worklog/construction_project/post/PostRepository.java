package com.arnor4eck.worklog.construction_project.post;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/** Репозиторий для постов
 * @see Post
 * */
@Repository
public interface PostRepository extends CrudRepository<Post, Long> {
    /** Поиск постов по ID полигона, к которому они прикреплены
     * @param projectId ID полигона
     * @return Массив - список постов
     * */
    List<Post> findByObjectId(Long projectId);
}
