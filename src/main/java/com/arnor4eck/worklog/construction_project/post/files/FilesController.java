package com.arnor4eck.worklog.construction_project.post.files;

import com.arnor4eck.worklog.utils.ExceptionResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.FileAlreadyExistsException;

@RestController
@RequestMapping("upload/")
@Slf4j
@AllArgsConstructor
public class FilesController {
    private FilesService filesService;

    @PostMapping
    public ResponseEntity<ExceptionResponse> uploadFile(@RequestParam("file")MultipartFile file){
        try{
            String path = filesService.createPath(file, 0,0);
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
