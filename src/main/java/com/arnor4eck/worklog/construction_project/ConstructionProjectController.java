package com.arnor4eck.worklog.construction_project;

import com.arnor4eck.worklog.construction_project.post.utils.PostDTO;
import com.arnor4eck.worklog.construction_project.post.PostService;
import com.arnor4eck.worklog.construction_project.post.files.FilesService;
import com.arnor4eck.worklog.construction_project.post.request.CreatePostRequest;
import com.arnor4eck.worklog.construction_project.utils.ConstructionProjectDTO;
import com.arnor4eck.worklog.construction_project.utils.CreateObjectRequest;
import com.arnor4eck.worklog.construction_project.utils.ProjectAlreadyExistsException;
import com.arnor4eck.worklog.utils.ExceptionResponse;
import lombok.AllArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/** Контроллер полигонов
 * @see ConstructionProject
 * @see ConstructionProjectDTO
 * */
@RestController
@RequestMapping(path = "objects/")
@AllArgsConstructor
public class ConstructionProjectController {
    private final ConstructionProjectService constructionProjectService;
    private final PostService postService;
    private final FilesService filesService;

    /** Получение всех полигонов авторизированного пользователя
     * @see ConstructionProjectService#getCurrentUserObjects()
     * */
    @GetMapping(path = "my_objects/")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<ConstructionProjectDTO> getUserObjects(){
        return constructionProjectService.getCurrentUserObjects();
    }

    /** Создание полигона
     * При успехе возвращает {@code 201}
     * @see ConstructionProjectService#createObject(CreateObjectRequest)
     * */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createObject(@RequestBody CreateObjectRequest request){
        constructionProjectService.createObject(request);
    }

    /** Получение полигона по ID
     * При успехе возвращает {@code 302}
     * @see ConstructionProjectService#getObject(long)
     * */
    @GetMapping("{id}/")
    @PreAuthorize("@constructionProjectService.hasAccess(authentication, #objectId)")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ConstructionProjectDTO getProject(@PathVariable("id") long objectId){
        return constructionProjectService.getObject(objectId);
    }

    /** Создание поста
     * При успехе возвращает {@code 201}
     * @see PostService#createPost(Long, CreatePostRequest)
     * */
    @PostMapping(path = "/access/{object_id}/create_post/")
    @PreAuthorize("@constructionProjectService.hasAccess(authentication, #objectId)")
    @ResponseStatus(HttpStatus.CREATED)
    public void createPost(@PathVariable("object_id") Long objectId, @RequestParam String title,
                           @RequestParam String content,
                           @RequestParam Long author,
                           @RequestParam(required = false) List<MultipartFile> files) throws FileAlreadyExistsException {

        postService.createPost(objectId, new CreatePostRequest(title, content,
                author, files == null || files.isEmpty() ? new ArrayList<>() : files));
    }

    /** Получение постов по ID полигона
     * При успехе возвращает {@code 202}
     * @see PostService#getPostsByObjectId(Long)
     * */
    @GetMapping("{object_id}/posts/")
    @PreAuthorize("@constructionProjectService.hasAccess(authentication, #objectId)")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<PostDTO> getPostsByObjectId(@PathVariable("object_id") Long objectId){
        return postService.getPostsByObjectId(objectId);
    }

    /** Получение поста по ID
     * При успехе возвращает {@code 202}
     * @see PostService#getPost(Long)
     * */
    @GetMapping(path = "{object_id}/{post_id}/")
    @PreAuthorize("@constructionProjectService.hasAccess(authentication, #objectId)")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public PostDTO getPost(@PathVariable("object_id") Long objectId,
                           @PathVariable("post_id") Long postId){
        return postService.getPost(postId);
    }

    /** Получение прикрепленного к посту файла
     * При успехе возвращает {@code 302}
     * @see FilesService#findFile(long, long, String)
     * */
    @GetMapping(path="{object_id}/{post_id}/file/")
    @PreAuthorize("@constructionProjectService.hasAccess(authentication, #objectId)")
    public ResponseEntity<Resource> getFile(@PathVariable("object_id") Long objectId,
                                            @PathVariable("post_id") Long postId,
                                            @RequestParam("file_name") String fileName) throws IOException {
        Path file = filesService.findFile(objectId,postId, fileName);

        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .contentType(MediaType.parseMediaType(
                        filesService.determineContentType(
                                filesService.getPostfix(file.getFileName().toString()))))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + file.getFileName().toString() + "\"")
                .body(new FileSystemResource(file));
    }

    @PostMapping(path="{object_id}/act/")
    @PreAuthorize("@constructionProjectService.hasAccess(authentication, #objectId)")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void loadActOfOpening(@PathVariable("object_id") Long objectId,
                                 @RequestParam(required = true) MultipartFile file) throws IOException {
        String pathToFile = filesService.createPathToObject(objectId) + File.separator + "act.pdf";
        filesService.deleteFile(pathToFile);
        filesService.saveFile(file, pathToFile);
    }

    @GetMapping(path="{object_id}/act/")
    @PreAuthorize("@constructionProjectService.hasAccess(authentication, #objectId)")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<Resource> getActOfOpening(@PathVariable("object_id") Long objectId) throws IOException {
        Path file = filesService.findFile(objectId, "act.pdf");
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .contentType(MediaType.parseMediaType(
                        filesService.determineContentType(
                                filesService.getPostfix(file.getFileName().toString()))))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + file.getFileName().toString() + "\"")
                .body(new FileSystemResource(file));
    }

    @PostMapping(path="{object_id}/works/")
    @PreAuthorize("@constructionProjectService.hasAccess(authentication, #objectId)")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void loadСompositionOfWorks(@PathVariable("object_id") Long objectId,
                                 @RequestParam(required = true) MultipartFile file) throws IOException {
        String pathToFile = filesService.createPathToObject(objectId) + File.separator + "works.pdf";
        filesService.deleteFile(pathToFile);
        filesService.saveFile(file, pathToFile);
    }

    @GetMapping(path="{object_id}/works/")
    @PreAuthorize("@constructionProjectService.hasAccess(authentication, #objectId)")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<Resource> getСompositionOfWorks(@PathVariable("object_id") Long objectId) throws IOException {
        Path file = filesService.findFile(objectId, "works.pdf");
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .contentType(MediaType.parseMediaType(
                        filesService.determineContentType(
                                filesService.getPostfix(file.getFileName().toString()))))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + file.getFileName().toString() + "\"")
                .body(new FileSystemResource(file));
    }

    /** Обработчик ошибок группы "Уже существует"
     * Возвращает сообщение ошибки и статус {@code 400}
     * @see ProjectAlreadyExistsException
     * @see FileAlreadyExistsException
     * */
    @ExceptionHandler({ProjectAlreadyExistsException.class, FileAlreadyExistsException.class})
    public ResponseEntity<ExceptionResponse> AlreadyExists(Exception exception){
        return new ResponseEntity<>(new ExceptionResponse(exception.getMessage()), HttpStatus.BAD_REQUEST);
    }

    /** Обработчик ошибки IOException
     * Возвращает сообщение ошибки и статус {@code 500}
     * @see IOException
     * */
    @ExceptionHandler(IOException.class)
    public ResponseEntity<ExceptionResponse> IOE(IOException e){
        return new ResponseEntity<>(new ExceptionResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
