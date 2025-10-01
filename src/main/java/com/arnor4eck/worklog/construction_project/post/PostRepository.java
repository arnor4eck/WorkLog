package com.arnor4eck.worklog.construction_project.post;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/** Репозиторий для постов
 * @see Post
 * */
@Repository
public interface PostRepository extends CrudRepository<Post, Long> {
}
