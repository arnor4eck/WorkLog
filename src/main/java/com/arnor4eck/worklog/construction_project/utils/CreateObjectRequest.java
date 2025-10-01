package com.arnor4eck.worklog.construction_project.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/** Запрос на создание полигона
 * */
@Data
@AllArgsConstructor
public class CreateObjectRequest {
    /** Название полигона
     * */
    private final String name;

    /** Описание полигона
     * */
    private final String description;

    /** Список координат
     * */
    private final List<List<Double>> coordinates;

    /** Список прикрепленных пользователей
     * */
    private final List<Long> users;

    /** ID пользователя, назначенного подрядчиком
     * */
    private final long contractor;

    /** ID пользователя, назначенного техническим надзором
     * */
    private final long supervision;
}