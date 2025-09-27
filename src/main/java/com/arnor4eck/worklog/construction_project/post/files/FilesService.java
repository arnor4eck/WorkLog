package com.arnor4eck.worklog.construction_project.post.files;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;

@Service
public class FilesService {

    public String createPath(MultipartFile file, long objectId, long postId){
        String sep = FileSystems.getDefault().getSeparator();
        return String.join(sep, "src", "main",
                        "resources", String.format("project_%d", objectId),
                        String.format("post_%d", postId), file.getOriginalFilename());
    }

    public String getPostfix(String fileName){
        return fileName.substring(fileName.lastIndexOf('.'));
    }

    public boolean acceptableFile(String fileName){
        return List.of(".doc", ".docx", ".pdf", ".txt", ".jpeg", ",png")
                .contains(this.getPostfix(fileName));
    }

    public void saveFile(MultipartFile file, String path) throws FileSystemException {
        Path curPath = Paths.get(path);

        if(!this.acceptableFile(file.getOriginalFilename()))
            throw new FileSystemException("Недопустимое расширение файла");
        if(Files.exists(curPath))
            throw new FileAlreadyExistsException("Файл уже существует");

        try {
            Files.createDirectories(curPath.getParent());
            Files.write(curPath, file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
