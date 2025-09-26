package com.arnor4eck.worklog.security.authorization;

import com.arnor4eck.worklog.security.authorization.jwt.JwtRequest;
import com.arnor4eck.worklog.security.authorization.jwt.JwtResponse;
import com.arnor4eck.worklog.security.authorization.jwt.JwtUtils;
import com.arnor4eck.worklog.user.User;
import com.arnor4eck.worklog.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "authhh/",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager manager;

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
                    new AuthError(HttpStatus.UNAUTHORIZED.value(), "Неправильный логин или пароль"),
                    HttpStatus.UNAUTHORIZED);
        }
    }
}
