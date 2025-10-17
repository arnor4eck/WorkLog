package com.arnor4eck.worklog.construction_project.post.request;

import com.arnor4eck.worklog.construction_project.post.utils.PostType;
import lombok.AllArgsConstructor;
import lombok.Data;

/** Запрос на создание поста
 * */
@Data
@AllArgsConstructor
public class CreatePostRequest {
    /** Заголовок поста
     * */
    private final String title;

    /** Содержимое поста
     * */
    private final String content;

    /** ID автора
     * */
    private final long author;

    /** Тип записи
     * */
    private final PostType status;
}
