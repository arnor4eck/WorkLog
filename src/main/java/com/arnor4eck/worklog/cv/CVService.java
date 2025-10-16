package com.arnor4eck.worklog.cv;

import com.arnor4eck.worklog.construction_project.ConstructionProject;
import lombok.AllArgsConstructor;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.stereotype.Service;

import java.nio.file.Path;

/** Сервис для CV
 * @see CVConfig
 * */
@Service
@AllArgsConstructor
public class CVService {
    /** Тессеракт
     * @see CVConfig#tesseract()
     * */
    private final Tesseract tesseract;

    /** Использование тессеракта
     * @param file Файл с текстом
     * @return Текст, содержащийся в файле
     * */
    public String extractText(Path file) throws TesseractException {
        return tesseract.doOCR(file.toFile());
    }
}
