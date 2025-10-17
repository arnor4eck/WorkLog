package com.arnor4eck.worklog.construction_project.post.utils;

import com.arnor4eck.worklog.construction_project.post.Post;
import com.arnor4eck.worklog.user.utils.UserDTO;
import lombok.Builder;
import lombok.Data;

import java.time.format.DateTimeFormatter;
import java.util.List;

/** Data-Transfer Object для {@link Post}
 * */
@Builder
@Data
public class PostDTO {
    /** Уникальный идентификатор поста
     * */
    private final Long id;

    /** Заголовок поста
     * */
        private final String title;

    /** Содержимое поста
     * */
    private final String content;

    /** Дата создания
     * */
    private final String createdAt;

    /** Список названий файлов, прикреплённых к посту
     * */
    private final List<String> files;

    /** Автор поста
     * @see UserDTO
     * */
    private final UserDTO author;

    private final String status;

    /** Создание DTO из класса {@link Post}
     * @param post {@link Post}
     * */
    public static PostDTO fromPost(Post post){
        return PostDTO.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .status(post.getStatus().getCode())
                .createdAt(post.getCreatedAt().format(DateTimeFormatter.ofPattern("dd/MM/yyyy '['HH:mm']'")))
                .files(post.getFiles())
                .author(UserDTO.formUser(post.getAuthor()))
                .build();
    }
}
