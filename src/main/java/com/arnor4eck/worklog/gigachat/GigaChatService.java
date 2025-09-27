package com.arnor4eck.worklog.gigachat;

import chat.giga.client.GigaChatClient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@AllArgsConstructor
public class GigaChatService {

    private final GigaChatClient gigaChatClient;

    public String fileContent(MultipartFile file){
        return "";
    }
}
