package com.arnor4eck.worklog.construction_project;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "objects/")
@AllArgsConstructor
public class ConstructionProjectController {
    private final ConstructionProjectService constructionProjectService;

    @GetMapping(path = "my_objects")
    public List<ConstructionProjectDTO> getUserObjects(){
        return constructionProjectService.getUserObjects();
    }
}
