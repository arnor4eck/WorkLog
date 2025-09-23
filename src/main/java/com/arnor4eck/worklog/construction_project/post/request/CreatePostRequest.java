package com.arnor4eck.worklog.construction_project.post.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreatePostRequest {
    private final String title;

    private final String content;

    private final long author;
}
