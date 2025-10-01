package com.arnor4eck.worklog.security;

import com.arnor4eck.worklog.construction_project.post.utils.PostNotFoundException;
import com.arnor4eck.worklog.construction_project.utils.ProjectNotFoundException;
import com.arnor4eck.worklog.utils.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/** Контроллер, позволяющий централизованно обрабатывать исключения во всём приложении
 * */
@RestControllerAdvice
public class ControllerAdvice {

    /** Обработка исключений при неудаче найти данные
     * @see UsernameNotFoundException
     * @see ProjectNotFoundException
     * @see PostNotFoundException
     * @return ResponseEntity с кодом {@code 404} и телом в виде сообщения об ошибке
     * */
    @ExceptionHandler({UsernameNotFoundException.class, ProjectNotFoundException.class,
                        PostNotFoundException.class})
    public ResponseEntity<ExceptionResponse> notFoundException(Exception exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ExceptionResponse(exception.getMessage()));
    }

    /** Обработка исключений при отказе в доступе
     * @see AccessDeniedException
     * @return ResponseEntity с кодом {@code 403} и телом в виде сообщения об ошибке
     * */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ExceptionResponse> accessDeniedException(AccessDeniedException exception) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(new ExceptionResponse(exception.getMessage()));
    }
}
