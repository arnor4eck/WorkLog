package com.arnor4eck.worklog.construction_project.post.files.utils;

/** Файл из локальной директории не найден
 * */
public class FileNotFoundException extends RuntimeException {
    public FileNotFoundException(String message) {
        super(message);
    }
}
