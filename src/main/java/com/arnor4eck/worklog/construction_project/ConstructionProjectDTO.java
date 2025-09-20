package com.arnor4eck.worklog.construction_project;

import com.arnor4eck.worklog.user.User;
import com.arnor4eck.worklog.user.UserDTO;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ConstructionProjectDTO {
    private final String name;

    private final String description;

    private final Double coordinateX;

    private final Double coordinateY;

    private final UserDTO responsibleContractor;

    private final UserDTO responsibleSupervision;

    public static ConstructionProjectDTO formConstructionProject(ConstructionProject constructionProject){
        return ConstructionProjectDTO.builder()
                .name(constructionProject.getName())
                .description(constructionProject.getDescription())
                .coordinateX(constructionProject.getCoordinateX())
                .coordinateY(constructionProject.getCoordinateY())
                .responsibleContractor(
                        UserDTO.formUser(
                                constructionProject.getResponsibleContractor()))
                .responsibleSupervision(
                        UserDTO.formUser(
                            constructionProject.getResponsibleSupervision()))
                .build();
    }
}
