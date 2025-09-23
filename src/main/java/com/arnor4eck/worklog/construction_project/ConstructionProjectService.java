package com.arnor4eck.worklog.construction_project;

import com.arnor4eck.worklog.construction_project.coordinates.Coordinates;
import com.arnor4eck.worklog.construction_project.utils.CreateObjectRequest;
import com.arnor4eck.worklog.construction_project.utils.ProjectAlreadyExistsException;
import com.arnor4eck.worklog.user.User;
import com.arnor4eck.worklog.user.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    public void createObject(CreateObjectRequest request){
        if(constructionProjectRepository.existsByName(request.getName()))
            throw new ProjectAlreadyExistsException("Объект с названием '%s' уже существует.".formatted(request.getName()));
        constructionProjectRepository.save(
                ConstructionProject.builder()
                        .name(request.getName())
                        .description(request.getDescription())
                        .responsibleContractor(userRepository.findById(request.getContractor()).orElseThrow(() -> new UsernameNotFoundException("Пользователя с id %d не существует".formatted(request.getContractor()))))
                        .responsibleSupervision(userRepository.findById(request.getSupervision()).orElseThrow(() -> new UsernameNotFoundException("Пользователя с id %d не существует".formatted(request.getSupervision()))))
                        .coordinates(
                                request.getCoordinates().stream()
                                        .map(c -> new Coordinates(c.getFirst(), c.getLast()))
                                        .toList())
                        .users(
                                request.getUsers().stream()
                                        .map(n ->
                                                userRepository.findById(n).orElseThrow(
                                                        () -> new UsernameNotFoundException("Пользователя с id %d не существует".formatted(n))))
                                        .collect(Collectors.toSet()))
                        .build());
    }
}
