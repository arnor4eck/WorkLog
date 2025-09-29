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

    @GetMapping(path="/{object_id}/{post_id}/")
    public ResponseEntity<Resource> getFile(@PathVariable("object_id") Long objectId,
                                     @PathVariable("post_id") Long postId,
                                     @RequestParam("file_name") String fileName){
        try {
            Path file = filesService.findFile(objectId,postId, fileName);

            return ResponseEntity.status(HttpStatus.FOUND)
                    .contentType(MediaType.parseMediaType(
                            filesService.determineContentType(
                                    filesService.getPostfix(file.getFileName().toString()))))
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=\"" + file.getFileName().toString() + "\"")
                    .body(new FileSystemResource(file));
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
