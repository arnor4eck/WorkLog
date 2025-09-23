package com.arnor4eck.worklog.construction_project.utils;

public class ProjectAlreadyExistsException extends RuntimeException{
    public ProjectAlreadyExistsException(String message){
        super(message);
    }
}
