package com.arnor4eck.worklog.construction_project;

import com.arnor4eck.worklog.user.User;
import com.arnor4eck.worklog.user.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class ConstructionProjectService {

    private final ConstructionProjectRepository constructionProjectRepository;

    private final UserRepository userRepository;

    public List<ConstructionProjectDTO> getUserObjects(){
        User currentUser = userRepository.findByEmail((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        return constructionProjectRepository
                .findByUsersId(currentUser.getId()).stream()
                .map(ConstructionProjectDTO::formConstructionProject)
                .toList();
    }
}
