package com.arnor4eck.worklog.security.authorization;

import com.arnor4eck.worklog.security.authorization.jwt.JwtRequest;
import com.arnor4eck.worklog.security.authorization.jwt.JwtResponse;
import com.arnor4eck.worklog.security.authorization.jwt.jwt_utils.JwtAuthUtils;
import com.arnor4eck.worklog.user.User;
import com.arnor4eck.worklog.user.UserRepository;
import com.arnor4eck.worklog.utils.ExceptionResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** Контроллер авторизации пользователя
 * */
@RestController
@RequestMapping(path = "auth/",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    /** @see UserRepository
     * */
    private final UserRepository userRepository;

    /** @see JwtAuthUtils
     * */
    private final JwtAuthUtils jwtUtils;

    /** @see com.arnor4eck.worklog.security.SecurityConfig#authenticationManager(UserDetailsService, PasswordEncoder)
     * */
    private final AuthenticationManager manager;

    /** Авторизация пользователя
     * @param authRequest Запрос на авторизацию {@link JwtRequest}
     * @return при успешной авторизации возвращает JWT с кодом {@code 200}, в противном - {@link ExceptionResponse} с кодом {@code 403}
     * */
    @PostMapping
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest){
        try{ // аутентификация по email и паролю
            manager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));

            User user = userRepository.findByEmail(authRequest.getEmail());
            String token = jwtUtils.generateToken(user);

            log.info("user '{}' authenticated", authRequest.getEmail());
            return ResponseEntity.ok(new JwtResponse(token));
        } catch (AuthenticationException e) {
            return new ResponseEntity<>(
                    new ExceptionResponse("Неправильный логин или пароль"),
                    HttpStatus.UNAUTHORIZED);
        }
    }
}
