package com.arnor4eck.worklog.construction_project.utils;

import com.arnor4eck.worklog.construction_project.ConstructionProject;
import com.arnor4eck.worklog.construction_project.coordinates.Coordinates;
import com.arnor4eck.worklog.construction_project.post.utils.PostDTO;
import com.arnor4eck.worklog.user.User;
import com.arnor4eck.worklog.user.UserDTO;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/** Data-Transfer Object для ConstructionProject
 * @see ConstructionProject
 * */
@Data
@Builder
public class ConstructionProjectDTO {

    /** Уникальный идентификатор
     * */
    private final Long id;

    /** Название полигона
     * */
    private final String name;

    /** Описание полигона
     * */
    private final String description;

    /** Координаты
     * @see Coordinates
     * */
    private final List<Coordinates> coordinates;

    /** Ответственный пользователь, назначаемый от подрядчика
     * @see UserDTO
     * */
    private final UserDTO responsibleContractor;

    /** Ответственный пользователь, назначаемый от технического надзора
     * @see UserDTO
     * */
    private final UserDTO responsibleSupervision;

    /** Записи, прикрепленные к объекту
     * @see PostDTO
     * */
    private final List<PostDTO> posts;

    /** Статус объекта
     * @see ObjectStatus
     * */
    private final String status;

    /** Создание DTO из класса {@link ConstructionProject}
     * @param constructionProject {@link ConstructionProject}
     * */
    public static ConstructionProjectDTO formConstructionProject(ConstructionProject constructionProject, List<PostDTO> posts){
        return ConstructionProjectDTO.builder()
                .id(constructionProject.getId())
                .name(constructionProject.getName())
                .description(constructionProject.getDescription())
                .coordinates(constructionProject.getCoordinates())
                .posts(posts)
                .status(constructionProject.getStatus().getCode())
                .responsibleContractor(
                        UserDTO.formUser(
                                constructionProject.getResponsibleContractor()))
                .responsibleSupervision(
                        UserDTO.formUser(
                            constructionProject.getResponsibleSupervision()))
                .build();
    }

}
