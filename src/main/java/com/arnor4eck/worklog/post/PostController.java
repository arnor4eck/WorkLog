package com.arnor4eck.worklog.post;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "posts/")
@AllArgsConstructor
public class PostController {

    private final PostRepository postRepository;

    @GetMapping
    public Iterable<Post> getAll(){
        return postRepository.findAll();
    }
}
