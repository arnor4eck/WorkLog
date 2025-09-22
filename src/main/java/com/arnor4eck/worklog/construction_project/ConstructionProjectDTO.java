package com.arnor4eck.worklog.construction_project;

import com.arnor4eck.worklog.construction_project.coordinates.Coordinates;
import com.arnor4eck.worklog.user.UserDTO;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ConstructionProjectDTO {

    private final String name;

    private final String description;

    private final List<Coordinates> coordinates;

    private final UserDTO responsibleContractor;

    private final UserDTO responsibleSupervision;

    public static ConstructionProjectDTO formConstructionProject(ConstructionProject constructionProject){
        return ConstructionProjectDTO.builder()
                .name(constructionProject.getName())
                .description(constructionProject.getDescription())
                .coordinates(constructionProject.getCoordinates())
                .responsibleContractor(
                        UserDTO.formUser(
                                constructionProject.getResponsibleContractor()))
                .responsibleSupervision(
                        UserDTO.formUser(
                            constructionProject.getResponsibleSupervision()))
                .build();
    }

}
