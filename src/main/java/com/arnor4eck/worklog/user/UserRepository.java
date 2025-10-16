package com.arnor4eck.worklog.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/** Репозиторий для пользователей
 * */
@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    /** Производит поиск пользователя в базе данных по email.
     * @param email Электронная почта
     * @return {@link User} найденный пользователь
     * */
    User findByEmail(String email);
}
