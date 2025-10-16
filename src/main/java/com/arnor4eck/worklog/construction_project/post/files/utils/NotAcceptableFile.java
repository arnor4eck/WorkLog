package com.arnor4eck.worklog.construction_project.post.files.utils;

import java.nio.file.FileSystemException;

public class NotAcceptableFile extends FileSystemException {
    public NotAcceptableFile(String file) {
        super(file);
    }
}
