package com.arnor4eck.worklog.construction_project;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "objects/")
@AllArgsConstructor
public class ConstructionProjectController {
    private final ConstructionProjectRepository constructionProjectRepository;

    @GetMapping
    public Iterable<ConstructionProject> getAll(){
        return constructionProjectRepository.findAll();
    }
}
