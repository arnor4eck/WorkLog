package com.arnor4eck.worklog.construction_project.utils;

/** Проект уже существует
 * */
public class ProjectAlreadyExistsException extends RuntimeException{
    public ProjectAlreadyExistsException(String message){
        super(message);
    }
}
