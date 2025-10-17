package com.arnor4eck.worklog.construction_project.post.utils;

import java.util.Arrays;

/** Перечисление возможных типов записей
 * */
public enum PostType {
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

    /** Тип записи (на русском языке)
     * */
    private final String code;

    PostType(String code){
        this.code = code;
    }

    String getCode(){
        return this.code;
    }

    /** Поиск поля перечисления по русскому типу записи
     * @param code Тип записи на русском языке
     * @return PostType - поле перечисления
     * @throws IllegalArgumentException В случае, если переданный {@link PostType#code} некорректен
     * */
    public static PostType fromCode(String code) throws IllegalArgumentException {
        return Arrays.stream(values())
                .filter(status -> status.code.equals(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Невалидный код: " + code));
    }
}
