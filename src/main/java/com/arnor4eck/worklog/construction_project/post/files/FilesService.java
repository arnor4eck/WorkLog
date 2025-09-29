package com.arnor4eck.worklog.construction_project.post.files;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.*;
import java.util.List;

@Service
public class FilesService {

    public String createPath(String fileName, long objectId, long postId){
        String sep = FileSystems.getDefault().getSeparator();
        return String.join(sep, "src", "main",
                        "resources", String.format("project_%d", objectId),
                        String.format("post_%d", postId), fileName);
    }

    public Path findFile(long objectId, long postId, String fileName) throws IOException {
        return Files.list(Paths.get(this.createPath("", objectId, postId)))
                .filter(f -> f.getFileName().toString().equals(fileName))
                .findFirst().orElseThrow(FileNotFoundException::new);
    }

    public String getPostfix(String fileName){
        return fileName.substring(fileName.lastIndexOf('.'));
    }

    public String determineContentType(String filename) {
        String extension = this.getPostfix(filename);
        return switch (extension) {
            case ".pdf" -> "application/pdf";
            case ".jpg", "jpeg" -> "image/jpeg";
            case ".png" -> "image/png";
            case ".doc" -> "application/msword";
            case ".docx" -> "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
            case ".zip" -> "application/zip";
            default -> "application/octet-stream";
        };
    }

    public void saveFile(MultipartFile file, String path) throws FileAlreadyExistsException {
        Path curPath = Paths.get(path);

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
