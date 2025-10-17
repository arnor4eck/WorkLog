package com.arnor4eck.worklog.construction_project.post.utils;

import com.arnor4eck.worklog.construction_project.utils.ObjectStatus;

import java.util.Arrays;

public enum PostStatus {
    ADDING_VIOLATIONS("Добавление нарушения"), // инспектор
    INITIATING_LABORATORY_SAMPLING("Инициирование лабораторного отбора"), // инспектор
    CONFIRMING_CORRECTIONS("Подтверждение исправления нарушения"), // инспектор
    REJECTING_CORRECTIONS_OF_ENTITIES("Отклонение исправления нарушений"), // инспектор
    ADDING_COMMENTS("Добавление замечаний"), // ССК
    CONFIRMING_CORRECTIONS_OF_VIOLATIONS("Подтверждение исправления замечаний"), // ССК
    REJECTING_CORRECTIONS_OF_VIOLATIONS("Отклонение исправления замечаний"), // ССК
    CORRECTING_DEVIATIONS("Исправление нарушений"), // строитель
    CORRECTING_COMMENTS("Исправление замечаний"), // строитель
    TTN("Добавление товарно-транспортной накладной"),
    PERFORMING_WORK("Выполнение работ");

    private final String code;

    PostStatus(String code){
        this.code = code;
    }

    String getCode(){
        return this.code;
    }

    public static PostStatus fromCode(String code) {
        return Arrays.stream(values())
                .filter(status -> status.code.equals(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Невалидный код: " + code));
    }
}
