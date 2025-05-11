package com.TickTock.TickTock.security.infrastructure.jwt;

import com.TickTock.TickTock.shared.application.dtos.response.ErrorHandlerResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {
        
        // Verificar si es una solicitud AJAX o API
        if (isRestRequest(request)) {
            // Para solicitudes API/REST, enviar error JSON
            ErrorHandlerResponse errorResponse = ErrorHandlerResponse.builder()
                    .error("Acceso Denegado")
                    .message(accessDeniedException.getMessage())
                    .summary("No tienes permisos suficientes para acceder a este recurso")
                    .timestamp(LocalDateTime.now())
                    .build();
            
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType("application/json");
            objectMapper.findAndRegisterModules(); // Para manejar LocalDateTime
            response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
        } else {
            // Para solicitudes de navegador, redirigir a la página de error HTML
            response.sendRedirect("/error/403");
        }
    }
    
    private boolean isRestRequest(HttpServletRequest request) {
        String accept = request.getHeader("Accept");
        String contentType = request.getHeader("Content-Type");
        String requestedWith = request.getHeader("X-Requested-With");
        
        return (accept != null && accept.contains("application/json")) ||
               (contentType != null && contentType.contains("application/json")) ||
               (requestedWith != null && requestedWith.equals("XMLHttpRequest")) ||
               request.getRequestURI().startsWith("/api/");
    }
}