package com.arnor4eck.worklog.gigachat;

import chat.giga.client.GigaChatClient;

import chat.giga.client.auth.AuthClient;
import chat.giga.client.auth.AuthClientBuilder;
import chat.giga.http.client.HttpClientException;
import chat.giga.model.ModelName;
import chat.giga.model.Scope;
import chat.giga.model.completion.ChatMessage;
import chat.giga.model.completion.CompletionRequest;
import chat.giga.model.file.UploadFileRequest;
import com.arnor4eck.worklog.construction_project.post.files.FilesService;
import com.arnor4eck.worklog.utils.ExceptionResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class GigaChatService {

    private enum MimeType{
        PDF(".pdf", "application/pdf"),
        JPEG(".jpeg", "image/jpeg"),
        PNG(".png", "image/png"),
        DOCX(".docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document"),
        TIFF(".tiff", "image/tiff"),
        BMP(".bmp", "image/bmp");

        private final String postfix;
        private final String type;

        MimeType(String postfix, String type){
            this.type = type;
            this.postfix = postfix;
        }

        public static MimeType findTypeByPostfix(String prefix){
            for(MimeType type : MimeType.values())
                if(type.postfix.equals(prefix))
                    return type;

            throw new IllegalArgumentException();
        }

        public String getType(){
            return this.type;
        }
    }

    private final GigaChatClient gigaChatClient;

    private final FilesService filesService;

    public ResponseEntity<String> fileContent(Path file){
        String fileID = (new UUID(0,0)).toString();
        String postfix = filesService.getPostfix(file.getFileName().toString());
        this.gigaChatClient.getListAvailableFile().data().forEach(System.out::println);

        try {
            fileID = this.uploadFile(file, postfix); //

            byte[] arr = this.gigaChatClient.downloadFile(fileID,
                    "");
            try(FileOutputStream stream = new FileOutputStream("testesttes.bmp")){
                stream.write(arr);
            }

            String content = this.getContent(postfix, fileID);
            return ResponseEntity.ok(content);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        catch (HttpClientException e){
            System.out.println(e.statusCode() + " " + e.bodyAsString());

            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        finally {
            if(!fileID.equals("00000000-0000-0000-0000-000000000000")){
                //this.deleteFile(fileID);
            }
        }
    }

    private String getContent(String postfix, String fileID){
        return switch(MimeType.findTypeByPostfix(postfix)){
            case PDF, DOCX -> this.contentForTextFiles(fileID);
            case JPEG, PNG, TIFF, BMP -> this.contentForImageFiles(fileID);
        };
    }

    private String contentForTextFiles(String fileID){
        return this.gigaChatClient.completions(CompletionRequest.builder()
                .model(ModelName.GIGA_CHAT_MAX)
                .message(ChatMessage.builder() // TODO prompt
                        .content("Конвертируй содержимое файла в текст, не пропуская ни одного символа")
                        .role(ChatMessage.Role.USER)
                        .attachment(fileID)
                        .build()).build()).toString();
    }

    private String contentForImageFiles(String fileID){
        return this.gigaChatClient.completions(CompletionRequest.builder()
                .model(ModelName.GIGA_CHAT_MAX)
                .message(ChatMessage.builder() // TODO prompt
                        .content("Конвертируй содержимое файла в текст, не пропуская ни одного символа")
                        .role(ChatMessage.Role.USER)
                        .attachment(fileID)
                        .build())
                .build()).toString();
    }

    private String uploadFile(Path file, String postfix) throws IOException {
        byte[] arr = Files.readAllBytes(file);
        System.out.println(postfix);

        var e = this.gigaChatClient.uploadFile(UploadFileRequest.builder()
                .file(arr)
                .mimeType(MimeType.findTypeByPostfix(postfix).getType())
                .fileName("file" + postfix)
                .purpose("general")
                .build());
        System.out.println(24141241);
        return e.id().toString();
    }

    private boolean deleteFile(String fileID){
        return this.gigaChatClient.deleteFile(fileID).deleted();
    }
}
