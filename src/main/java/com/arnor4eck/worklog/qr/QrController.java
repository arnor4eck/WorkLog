package com.arnor4eck.worklog.qr;

import com.arnor4eck.worklog.security.authorization.jwt.JwtAccessForAction;
import com.arnor4eck.worklog.security.authorization.jwt.JwtResponse;
import com.arnor4eck.worklog.security.authorization.jwt.jwt_utils.JwtAccessUtils;
import com.arnor4eck.worklog.user.User;
import com.arnor4eck.worklog.utils.ExceptionResponse;
import com.google.zxing.WriterException;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.descriptor.web.ContextHandler;
import org.springframework.core.io.Resource;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("qr/")
@AllArgsConstructor
@Slf4j
public class QrController {
    private final QrService qrService;

    private final JwtAccessUtils jwtAccessUtils;

    @GetMapping("generate/")
    public ResponseEntity<Resource> downloadQr() throws IOException, WriterException {
        String currentTime = LocalDateTime.now().toString();
        String url = "http://127.0.0.1:8080/qr/read/?r=";
        byte[] qrBytes = qrService.generateQr(url + currentTime, 250, 250);
        ByteArrayResource qr = qrService.qrResource(qrBytes);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=%s.png".formatted(currentTime))
                .contentType(MediaType.IMAGE_PNG)
                .contentLength(qrBytes.length)
                .body(qr);
    }

    @GetMapping("read/")
    public ResponseEntity<?> getJwt(@RequestParam(value = "r") String time){
        Duration dur = Duration.between(LocalDateTime.parse(time, DateTimeFormatter.ISO_DATE_TIME),
                LocalDateTime.now());

        if(dur.getSeconds() > 21600L){
            return new ResponseEntity<>(new ExceptionResponse("Срок жизни QR истёк"),
                    HttpStatus.CONFLICT);
        }

        String token = jwtAccessUtils.generateToken(time);
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        log.info("user '{}' authenticated with qr created at '{}'", email, time);
        return ResponseEntity.ok(new JwtResponse(token));
    }
}
