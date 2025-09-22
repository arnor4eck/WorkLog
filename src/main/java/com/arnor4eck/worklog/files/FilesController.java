package com.arnor4eck.worklog.files;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.FileAlreadyExistsException;
import java.nio.file.FileSystems;
import java.nio.file.Paths;

@RestController
@RequestMapping("upload/")
@Slf4j
@AllArgsConstructor
public class FilesController {
    private FilesService filesService;

    @PostMapping
    public ResponseEntity<String> uploadFile(@RequestParam("file")MultipartFile file){
        try{
            String path = filesService.createPath(file, 0,0);
            filesService.saveFile(file, path);
            return new ResponseEntity<>(path, HttpStatus.CREATED);
        }catch (FileAlreadyExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }
}
