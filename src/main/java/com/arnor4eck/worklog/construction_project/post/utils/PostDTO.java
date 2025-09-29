package com.arnor4eck.worklog.construction_project.post.utils;

import com.arnor4eck.worklog.construction_project.post.Post;
import com.arnor4eck.worklog.user.UserDTO;
import lombok.Builder;
import lombok.Data;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Builder
@Data
public class PostDTO {
    private final Long id;
    private final String title;
    private final String content;
    private final String createdAt;
    private final List<String> files;
    private final UserDTO author;

    public static PostDTO fromPost(Post post){
        return PostDTO.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .createdAt(post.getCreatedAt().format(DateTimeFormatter.ofPattern("dd/MM/yyyy '['HH:mm']'")))
                .files(post.getFiles())
                .author(UserDTO.formUser(post.getAuthor()))
                .build();
    }
}
