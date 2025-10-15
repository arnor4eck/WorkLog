package com.arnor4eck.worklog.qr;

import com.google.zxing.WriterException;
import org.springframework.core.io.Resource;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.LocalDateTime;

@RestController
@RequestMapping("qr/")
@AllArgsConstructor
public class QrController {

    private final QrService qrService;

    @GetMapping("generate/")
    public ResponseEntity<Resource> downloadQr() throws IOException, WriterException {
        String currentTime = LocalDateTime.now().toString();
        byte[] qrBytes = qrService.generateQr(currentTime, 250, 250);
        ByteArrayResource qr = qrService.qrResource(qrBytes);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=%s.png".formatted(currentTime))
                .contentType(MediaType.IMAGE_PNG)
                .contentLength(qrBytes.length)
                .body(qr);
    }
}
