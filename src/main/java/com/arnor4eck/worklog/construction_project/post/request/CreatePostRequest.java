package com.arnor4eck.worklog.construction_project.post.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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

    /** Список файлов
     * @see MultipartFile
     * */
    private List<MultipartFile> files;
}
