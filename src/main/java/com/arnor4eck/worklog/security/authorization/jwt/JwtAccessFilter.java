//package com.arnor4eck.worklog.security.authorization.jwt;
//
//import com.arnor4eck.worklog.security.authorization.jwt.jwt_utils.JwtAccessUtils;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.AllArgsConstructor;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//
///** Фильтр для QR-кода
// * */
//@Component
//@AllArgsConstructor
//public class JwtAccessFilter extends OncePerRequestFilter {
//
//    /** @see JwtAccessUtils
//     * */
//    private final JwtAccessUtils jwtAccessUtils;
//
//    /** Прохождение через фильтр
//     * */
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        if(request.getRequestURI().contains("/access/")) {
//            String header = request.getHeader("X-Access-Action-Header");
//
//            if(header == null) {
//                sendErrorResponse(response, "The header is missing");
//                return;
//            }
//
//            if(!jwtAccessUtils.validate(header)) {
//                sendErrorResponse(response, "Invalid JWT");
//                return;
//            }
//
//            filterChain.doFilter(request, response);
//        } else {
//            filterChain.doFilter(request, response);
//        }
//    }
//
//    /** В случае возникновения ошибки возвращается ее номер и сообщение
//     * */
//    private void sendErrorResponse(HttpServletResponse response, String message) throws IOException {
//        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//        response.setContentType("application/json");
//        response.getWriter().write("{\"error\": \"Unauthorized\", \"message\": \"" + message + "\"}");
//    }
//}
