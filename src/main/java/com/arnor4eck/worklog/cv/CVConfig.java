package com.arnor4eck.worklog.cv;

import net.sourceforge.tess4j.Tesseract;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;

/** Конфигурация CV приложения
 * */
@Configuration
public class CVConfig {

    /** Настройки Тессеракта
     * */
    @Bean
    public Tesseract tesseract(){
        Tesseract tesseract = new Tesseract();
        tesseract.setLanguage("rus+eng");
        tesseract.setDatapath(findTessDataPath());

        return tesseract;
    }

    /** Находит директорию с русским и английским языками
     * */
    private String findTessDataPath() {
        String[] possiblePaths = {
                "/usr/share/tessdata",                 // основной путь в Alpine
                "/usr/share/tesseract-ocr/tessdata",   // альтернативный путь
                "/usr/share/tesseract-ocr/5/tessdata", // версия 5
                "/usr/share/tesseract-ocr/4/tessdata"  // версия 4
        };

        for (String path : possiblePaths) {
            File tessDataDir = new File(path);

            if (tessDataDir.exists() && tessDataDir.isDirectory()) {
                // всех файлов в директории
                String[] files = tessDataDir.list();

                // наличие русских и английских файлов
                File rusFile = new File(path, "rus.traineddata");
                File engFile = new File(path, "eng.traineddata");

                if (rusFile.exists() && engFile.exists()) {
                    return path;
                }
            }
        }

        throw new RuntimeException("Tessdata directory not found with required languages (rus, eng). " +
                "Please check that Tesseract is properly installed in the final Docker image.");
    }
}
