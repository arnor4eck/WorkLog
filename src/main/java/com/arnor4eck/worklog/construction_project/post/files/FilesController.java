package com.arnor4eck.worklog.construction_project.post.files;

import com.arnor4eck.worklog.utils.ExceptionResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Path;

@RestController
@RequestMapping("upload/")
@Slf4j
@AllArgsConstructor
public class FilesController {
    private FilesService filesService;

    @PostMapping
    public ResponseEntity<ExceptionResponse> uploadFile(@RequestParam("file")MultipartFile file){
        try{
            String path = filesService.createPath(file.getOriginalFilename(), 0,0);
            filesService.saveFile(file, path);

            log.info("file {} was uploaded", path);
            return new ResponseEntity<>(new ExceptionResponse(path),
                    HttpStatus.CREATED);
        }catch (FileAlreadyExistsException e) {
            return new ResponseEntity<>(new ExceptionResponse(e.getMessage()),
                    HttpStatus.BAD_REQUEST);
        }

    }
}
