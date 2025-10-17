package com.arnor4eck.worklog.construction_project.utils;

/** Перечисление возможных статусов полигона
 * */
public enum ObjectStatus {
    ACTIVE("active"), COMPLETED("completed"), PLANNED("planned");

    /** Статус полигона (на русском языке)
     * */
    private final String code;

    ObjectStatus(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
