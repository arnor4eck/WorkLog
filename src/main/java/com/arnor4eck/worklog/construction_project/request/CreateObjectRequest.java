package com.arnor4eck.worklog.construction_project.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CreateObjectRequest {
    private final String name;

    private final String description;

    private final List<List<Double>> coordinates;

    private final List<Long> users;

    private final long contractor;

    private final long supervision;
}