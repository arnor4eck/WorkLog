package com.arnor4eck.worklog.construction_project;

import com.arnor4eck.worklog.user.User;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ConstructionProjectService {

    private final ConstructionProjectRepository constructionProjectRepository;

    public List<ConstructionProjectDTO> getUserObjects(){
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return constructionProjectRepository
                .findByUsersId(currentUser.getId()).stream()
                .map(ConstructionProjectDTO::formConstructionProject)
                .toList();
    }
}
