package com.arnor4eck.worklog.construction_project.post;

import com.arnor4eck.worklog.construction_project.ConstructionProjectRepository;
import com.arnor4eck.worklog.construction_project.post.files.FilesService;
import com.arnor4eck.worklog.construction_project.post.utils.PostDTO;
import com.arnor4eck.worklog.construction_project.post.utils.PostNotFoundException;
import com.arnor4eck.worklog.construction_project.post.utils.PostStatus;
import com.arnor4eck.worklog.construction_project.utils.ProjectNotFoundException;
import com.arnor4eck.worklog.construction_project.post.request.CreatePostRequest;
import com.arnor4eck.worklog.user.User;
import com.arnor4eck.worklog.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.FileAlreadyExistsException;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.security.core.context.SecurityContextHolder.getContext;

/** Сервис для постов
 * @see Post
 * */
@Service
@AllArgsConstructor
public class PostService {
    private final FilesService filesService;

    private final PostRepository postRepository;

    private final UserRepository userRepository;

    private final ConstructionProjectRepository constructionProjectRepository;

    /** Создание поста
     * @param objectId ID полигона
     * @param request Запрос на создание поста
     * @throws FileAlreadyExistsException Если файл с таким именем уже существует
     * @throws UsernameNotFoundException Если пользователя с переданным ID не существует
     * @throws ProjectNotFoundException Если полигона с переданным ID не существует
     * @see FilesService#saveFile(MultipartFile, String)
     * */
    public void createPost(Long objectId, CreatePostRequest request, List<MultipartFile> requestFiles) throws FileAlreadyExistsException {
        Post post = postRepository.save(Post.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .status(request.getStatus())
                .author(userRepository.findById(
                        request.getAuthor()).orElseThrow(
                                () -> new UsernameNotFoundException("Пользователя с id '%d' не существует".formatted(request.getAuthor()))))
                .object(constructionProjectRepository.findById(
                        objectId).orElseThrow(() -> new ProjectNotFoundException("Полигона с id '%d' не существует".formatted(objectId))))
                .build());

        List<String> files = new LinkedList<>();

        for(MultipartFile file : requestFiles){
            filesService.saveFile(file, filesService.createPath(file.getOriginalFilename(), objectId, post.getId()));
            files.add(file.getOriginalFilename());
        }
        post.setFiles(files);
        postRepository.save(post);
    }

    public void createPostBySender(Long objectId, String title, String content, String status, List<MultipartFile> requestFiles) throws FileAlreadyExistsException{
        User user = userRepository.findByEmail(
                (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        this.createPost(objectId,
                new CreatePostRequest(title, content, user.getId(), PostStatus.fromCode(status)),
                requestFiles);
    }

    /** Получение поста по ID
     * @param postId ID поста
     * @see PostDTO
     * @throws PostNotFoundException Если поста с переданным ID не существует
     * */
    public PostDTO getPost(Long postId){
        return PostDTO.fromPost(this.postRepository.findById(postId)
                .orElseThrow(() ->
                        new PostNotFoundException("Поста с id '%d' не существует".formatted(postId))));
    }

    public List<PostDTO> getPostsByObjectId(Long objectId){
        return postRepository.findByObjectId(objectId).stream().map(PostDTO::fromPost).toList();
    }
}
