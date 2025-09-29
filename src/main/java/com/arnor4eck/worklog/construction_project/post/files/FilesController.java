package com.arnor4eck.worklog.construction_project.post.files;

import com.arnor4eck.worklog.gigachat.GigaChatService;
import com.arnor4eck.worklog.utils.ExceptionResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.FileSystemException;
import java.nio.file.Files;

@RestController
@RequestMapping("upload/")
@Slf4j
@AllArgsConstructor
public class FilesController {
    private FilesService filesService;
    private GigaChatService gigaChatService;

    @PostMapping
    public ResponseEntity<ExceptionResponse> uploadFile(@RequestParam("file")MultipartFile file){
        try{

            String path = filesService.createPath(file.getOriginalFilename(), 0,0);
            filesService.saveFile(file, path);

            log.info("file {} was uploaded", path);
            return new ResponseEntity<>(new ExceptionResponse(path),
                    HttpStatus.CREATED);
        }catch (FileSystemException e) {
            return new ResponseEntity<>(new ExceptionResponse(e.getMessage()),
                    HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping(path="{object_id}/{post_id}/")
    public ResponseEntity<String> getFile(@PathVariable("object_id") Long objectId,
                          @PathVariable("post_id") Long postId,
                          @RequestParam("fileName") String fileName) throws IOException {
        return gigaChatService.fileContent(
                filesService.findFile(objectId, postId, fileName));
    }
}
