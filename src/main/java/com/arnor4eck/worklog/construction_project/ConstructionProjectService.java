package com.arnor4eck.worklog.construction_project;

import com.arnor4eck.worklog.construction_project.coordinates.Coordinates;
import com.arnor4eck.worklog.construction_project.post.PostRepository;
import com.arnor4eck.worklog.construction_project.post.files.FilesService;
import com.arnor4eck.worklog.construction_project.post.utils.PostDTO;
import com.arnor4eck.worklog.construction_project.utils.ConstructionProjectDTO;
import com.arnor4eck.worklog.construction_project.utils.CreateObjectRequest;
import com.arnor4eck.worklog.construction_project.utils.ProjectAlreadyExistsException;
import com.arnor4eck.worklog.construction_project.utils.ProjectNotFoundException;
import com.arnor4eck.worklog.user.User;
import com.arnor4eck.worklog.user.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

/** Сервис для полигонов
 * @see ConstructionProject
 * */
@Service
@AllArgsConstructor
@Slf4j
public class ConstructionProjectService {

    private final ConstructionProjectRepository constructionProjectRepository;

    private final PostRepository postRepository;

    private final UserRepository userRepository;

    private final FilesService filesService;

    /** Проверяет, есть ли у пользователя доступ к данному полигону
     * @param projectId ID полигона
     * @see ConstructionProjectRepository#projectContainsUser(Long, String)
     * @throws AccessDeniedException В случае, если {@link ConstructionProjectRepository#projectContainsUser(Long, String)} возвращает false
     * */
    public boolean hasAccess(Authentication auth, Long projectId){
        String email = (String) auth.getPrincipal();

        if(!constructionProjectRepository.projectContainsUser(projectId, email))
            throw new AccessDeniedException("У вас нет доступа к этому ресурсу.");

        return true;
    }

    /** Возвращает список полигонов пользователя по его ID
     * @param userId ID пользователя
     * @return Список объектов
     * @see ConstructionProjectDTO
     * */
    public List<ConstructionProjectDTO> getUserObjects(long userId){
        return constructionProjectRepository
                .findByUsersId(userId).stream()
                .map(p -> ConstructionProjectDTO.formConstructionProject(p, null))
                .toList();
    }

    /** Возвращает список полигонов авторизированного пользователя
     * @return Список объектов
     * */
    public List<ConstructionProjectDTO> getCurrentUserObjects(){
        User currentUser = userRepository.findByEmail((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        return this.getUserObjects(currentUser.getId());
    }

    /** Вовзвращает полигон по его ID с записями к полигону
     * @param id ID полигона
     * @return Полигон
     * @see ConstructionProjectDTO
     * @throws ProjectNotFoundException Если не найден полигон
     * */
    public ConstructionProjectDTO getObject(long id){
        ConstructionProject object = constructionProjectRepository.findById(id)
                .orElseThrow(() -> new ProjectNotFoundException("Полигона с id '%d' не существует".formatted(id)));


        return ConstructionProjectDTO.formConstructionProject(object,
                postRepository.findByObjectId(id).stream().map(PostDTO::fromPost).toList());
    }

    /** Создание полигона
     * @param request Запрос на создание полигона
     * @see CreateObjectRequest
     * @throws ProjectAlreadyExistsException Если полигон с таким названием уже существует
     * */
    public void createObject(CreateObjectRequest request){
        if(constructionProjectRepository.existsByName(request.getName()))
            throw new ProjectAlreadyExistsException("Объект с названием '%s' уже существует.".formatted(request.getName()));

        ConstructionProject obj = constructionProjectRepository.save(
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
        try{
            Files.createDirectories(Path.of(filesService.createPathToObject(obj.getId())));
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }
}
