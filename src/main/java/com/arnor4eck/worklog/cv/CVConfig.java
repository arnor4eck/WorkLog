package com.arnor4eck.worklog.cv;

import net.sourceforge.tess4j.Tesseract;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
        tesseract.setDatapath("D:\\Tesseract-OCR\\tessdata");

        return tesseract;
    }
}
