package com.arnor4eck.worklog.construction_project.post.utils;

/** Поста не существует
 * */
public class PostNotFoundException extends RuntimeException {
    public PostNotFoundException(String message) {
      super(message);
    }
}


