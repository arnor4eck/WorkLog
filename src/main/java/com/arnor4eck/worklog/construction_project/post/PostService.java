package com.arnor4eck.worklog.construction_project.post;

import com.arnor4eck.worklog.construction_project.ConstructionProjectRepository;
import com.arnor4eck.worklog.construction_project.utils.ProjectNotFoundException;
import com.arnor4eck.worklog.construction_project.post.request.CreatePostRequest;
import com.arnor4eck.worklog.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    private final UserRepository userRepository;

    private final ConstructionProjectRepository constructionProjectRepository;

    public void createPost(Long objectId, CreatePostRequest request){
        postRepository.save(Post.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .author(userRepository.findById(
                        request.getAuthor()).orElseThrow(
                                () -> new UsernameNotFoundException("Пользователя с id '%d' не существует".formatted(request.getAuthor()))))
                .object(constructionProjectRepository.findById(
                        objectId).orElseThrow(() -> new ProjectNotFoundException("Полигона с id '%d' не существует".formatted(objectId))))
                .build());

    }
}
