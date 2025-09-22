package com.arnor4eck.worklog.construction_project;

import com.arnor4eck.worklog.construction_project.request.CreateObjectRequest;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "objects/")
@AllArgsConstructor
public class ConstructionProjectController {
    private final ConstructionProjectService constructionProjectService;

    @GetMapping(path = "my_objects/")
    public List<ConstructionProjectDTO> getUserObjects(){
        return constructionProjectService.getUserObjects();
    }

    @PostMapping
    public void createObject(@RequestBody CreateObjectRequest request){
        constructionProjectService.createObject(request);
    }
}
