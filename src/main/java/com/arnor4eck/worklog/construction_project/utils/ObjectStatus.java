package com.arnor4eck.worklog.construction_project.utils;

import java.util.Arrays;

public enum ObjectStatus {
    ACTIVE("active"), COMPLETED("complited"), PLANNED("complited");

    private final String code;

    ObjectStatus(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static ObjectStatus fromCode(String code) {
        return Arrays.stream(values())
                .filter(status -> status.code.equals(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown code: " + code));
    }
}
