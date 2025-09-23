package com.arnor4eck.worklog.construction_project;

import com.arnor4eck.worklog.construction_project.post.PostService;
import com.arnor4eck.worklog.construction_project.post.request.CreatePostRequest;
import com.arnor4eck.worklog.construction_project.utils.ConstructionProjectDTO;
import com.arnor4eck.worklog.construction_project.utils.CreateObjectRequest;
import com.arnor4eck.worklog.construction_project.utils.ProjectAlreadyExistsException;
import com.arnor4eck.worklog.utils.ExceptionResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.FileAlreadyExistsException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "objects/")
@AllArgsConstructor
public class ConstructionProjectController {
    private final ConstructionProjectService constructionProjectService;
    private final PostService postService;

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

        CreatePostRequest request = new CreatePostRequest(title, content,
                author, files == null ? new ArrayList<>() : files);

        postService.createPost(objectId, request);
    }

    @ExceptionHandler({ProjectAlreadyExistsException.class, FileAlreadyExistsException.class})
    public ResponseEntity<ExceptionResponse> AlreadyExists(Exception exception){
        return new ResponseEntity<>(new ExceptionResponse(exception.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
