package com.arnor4eck.worklog.security.authorization.jwt;


import com.arnor4eck.worklog.security.authorization.jwt.jwt_utils.JwtAccessUtils;
import com.arnor4eck.worklog.security.authorization.jwt.jwt_utils.JwtAuthUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

/** Фильтр для JWT
 * */
@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {
    /** @see JwtAuthUtils
     * */
    private final JwtAuthUtils jwtUtils;

    /** Прохождение через цепочку фильтров
     * */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        if(authHeader != null && authHeader.startsWith("Bearer ")){
            String jwt = authHeader.substring(7); // всё что после Bearer
            if(jwtUtils.validate(jwt)) {
                String email = jwtUtils.getEmail(jwt);

                if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                            email, null, jwtUtils.getRoles(jwt).stream().map(SimpleGrantedAuthority::new).toList()
                    );
                    token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(token);
                }
            }else{
                sendErrorResponse(response, "Invalid JWT");
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    /** В случае возникновения ошибки возвращается ее номер и сообщение
     * */
    private void sendErrorResponse(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write("{\"error\": \"Unauthorized\", \"message\": \"" + message + "\"}");
    }
}
