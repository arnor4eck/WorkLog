package com.arnor4eck.worklog.construction_project.utils;

/** Проект не найден
 * */
public class ProjectNotFoundException extends RuntimeException{
    public ProjectNotFoundException(String message){
        super(message);
    }
}
