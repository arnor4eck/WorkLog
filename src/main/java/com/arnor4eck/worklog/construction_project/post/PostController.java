package com.arnor4eck.worklog.construction_project.post;

import com.arnor4eck.worklog.construction_project.post.request.CreatePostRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "posts/")
@AllArgsConstructor
public class PostController {

    private final PostRepository postRepository;

    private final PostService postService;

    @GetMapping
    public Iterable<Post> getAll(){
        return postRepository.findAll();
    }

    /*
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void createPostWithFiles(@ModelAttribute CreatePostRequest request){
        postService.createPost(1L, request);
    }*/
}
