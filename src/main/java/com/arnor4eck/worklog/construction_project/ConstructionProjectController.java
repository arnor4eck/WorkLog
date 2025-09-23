package com.arnor4eck.worklog.construction_project;

import com.arnor4eck.worklog.construction_project.post.PostService;
import com.arnor4eck.worklog.construction_project.post.request.CreatePostRequest;
import com.arnor4eck.worklog.construction_project.utils.CreateObjectRequest;
import com.arnor4eck.worklog.construction_project.utils.ProjectAlreadyExistsException;
import com.arnor4eck.worklog.utils.ExceptionResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("{id}/create_post/")
    @ResponseStatus(HttpStatus.CREATED)
    public void createPost(@PathVariable("id") Long objectId, @RequestBody CreatePostRequest request){
        postService.createPost(objectId, request);
    }


    @ExceptionHandler(ProjectAlreadyExistsException.class)
    public ResponseEntity<ExceptionResponse> projectAlreadyExists(ProjectAlreadyExistsException exception){
        return new ResponseEntity<>(new ExceptionResponse(exception.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
