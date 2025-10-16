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
    List<Post> findByObjectId(Long projectId);
}
