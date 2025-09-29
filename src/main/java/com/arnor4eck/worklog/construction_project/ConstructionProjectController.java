package com.arnor4eck.worklog.construction_project;

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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "objects/")
@AllArgsConstructor
public class ConstructionProjectController {
    private final ConstructionProjectService constructionProjectService;
    private final PostService postService;
    private final FilesService filesService;

    @GetMapping(path = "my_objects/")
    public List<ConstructionProjectDTO> getUserObjects(){
        return constructionProjectService.getUserObjects();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createObject(@RequestBody CreateObjectRequest request){
        constructionProjectService.createObject(request);
    }

    @GetMapping("{id}/")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ConstructionProjectDTO getProject(@PathVariable("id") long objectId){
        return constructionProjectService.getObject(objectId);
    }

    @PostMapping(path = "{id}/create_post/")
    @ResponseStatus(HttpStatus.CREATED)
    public void createPost(@PathVariable("id") Long objectId, @RequestParam String title,
                           @RequestParam String content,
                           @RequestParam Long author,
                           @RequestParam(required = false) List<MultipartFile> files) throws FileAlreadyExistsException {

        postService.createPost(objectId, new CreatePostRequest(title, content,
                author, files == null ? new ArrayList<>() : files));
    }

    @GetMapping(path="/{object_id}/{post_id}/")
    public ResponseEntity<Resource> getFile(@PathVariable("object_id") Long objectId,
                                            @PathVariable("post_id") Long postId,
                                            @RequestParam("file_name") String fileName){
        try {
            Path file = filesService.findFile(objectId,postId, fileName);

            return ResponseEntity.status(HttpStatus.FOUND)
                    .contentType(MediaType.parseMediaType(
                            filesService.determineContentType(
                                    filesService.getPostfix(file.getFileName().toString()))))
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=\"" + file.getFileName().toString() + "\"")
                    .body(new FileSystemResource(file));
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @ExceptionHandler({ProjectAlreadyExistsException.class, FileAlreadyExistsException.class})
    public ResponseEntity<ExceptionResponse> AlreadyExists(Exception exception){
        return new ResponseEntity<>(new ExceptionResponse(exception.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
