package com.arnor4eck.worklog.construction_project.post.files;

import com.arnor4eck.worklog.construction_project.post.PostService;
import com.arnor4eck.worklog.construction_project.post.utils.PostNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.*;
import java.util.List;

/** Сервис для Файлов
 * */
@Service
public class FilesService {
    /** Создает локальный путь к файлу
     * @param postId ID поста
     * @param objectId ID полигона
     * @param fileName Название файла
     * @return Строка - путь к файлу
     * */
    public String createPath(String fileName, long objectId, long postId){
        String sep = FileSystems.getDefault().getSeparator();
        return String.join(sep, "src", "main",
                        "resources", String.format("project_%d", objectId),
                        String.format("post_%d", postId), fileName);
    }

    /** Нахождение файла в файловой системе
     * @param postId ID поста
     * @param objectId ID полигона
     * @param fileName Название файла
     * @throws FileNotFoundException Если файл не был найден
     * @return Path - файл
     * */
    public Path findFile(long objectId, long postId, String fileName) throws IOException {
        Path path = Paths.get(this.createPath("", objectId, postId));
        if(!path.toFile().exists())
            throw new PostNotFoundException("Пост с id '%d' не найден.".formatted(postId));
        return Files.list(path)
                .filter(f -> f.getFileName().toString().equals(fileName))
                .findFirst().orElseThrow(() -> new FileNotFoundException("Файл не найден."));
    }

    /** Возвращает расширение файла
     * @param fileName Название файла
     * @return Строка - расширение файла
     * */
    public String getPostfix(String fileName){
        return fileName.substring(fileName.lastIndexOf('.'));
    }

    /** Тип данных для запроса
     * */
    public String determineContentType(String filename) {
        return switch (this.getPostfix(filename)) {
            case ".pdf" -> "application/pdf";
            case ".jpg", "jpeg" -> "image/jpeg";
            case ".png" -> "image/png";
            case ".doc" -> "application/msword";
            case ".docx" -> "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
            case ".zip" -> "application/zip";
            default -> "application/octet-stream";
        };
    }

    /** Сохранение файла в локальную файловую систему
     * @param file Файла
     * @param path Путь
     * @throws FileAlreadyExistsException Если файл уже существует
     * */
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
