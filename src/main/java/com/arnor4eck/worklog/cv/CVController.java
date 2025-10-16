package com.arnor4eck.worklog.cv;

import com.arnor4eck.worklog.construction_project.post.files.FilesService;
import lombok.AllArgsConstructor;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/** Контроллер CV
 * @see CVConfig#tesseract()
 * */
@RestController
@AllArgsConstructor
@RequestMapping("cv/")
public class CVController {
    /** @see CVService
     * */
    private final CVService cvService;

    /** @see FilesService
     * */
    private final FilesService filesService;

    /** Преобразование файла в текст
     * */
    @GetMapping("{object_id}/{post_id}/")
    @PreAuthorize("@constructionProjectService.hasAccess(authentication, #objectId)")
    public String convertToText(@PathVariable("object_id") long objectId,
                      @PathVariable("post_id") long postId,
                      @RequestParam("file_name") String file_name) throws TesseractException, IOException {
        return cvService.extractText(filesService.findFile(objectId, postId, file_name));
    }
}
