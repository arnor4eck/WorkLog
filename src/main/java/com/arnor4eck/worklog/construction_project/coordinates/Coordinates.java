package com.arnor4eck.worklog.construction_project.coordinates;

import com.arnor4eck.worklog.construction_project.ConstructionProject;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
public class Coordinates {
    @Column(name = "coordinate_x", nullable = false)
    private Double x;

    @Column(name = "coordinate_y", nullable = false)
    private Double y;

    public Coordinates(double coordinateX, double coordinateY){
        this.x = coordinateX;
        this.y = coordinateY;
    }
}
