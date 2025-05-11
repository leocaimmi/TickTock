package com.TickTock.TickTock.security.infrastructure.jwt;

import com.TickTock.TickTock.shared.application.dtos.response.ErrorHandlerResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/error")
public class JwtController implements org.springframework.boot.web.servlet.error.ErrorController {

    @GetMapping("/401")
    public String handleUnauthorized(Model model) {
        ErrorHandlerResponse errorResponse = ErrorHandlerResponse.builder()
                .error("No Autorizado")
                .message("No tienes autorización para acceder a este recurso")
                .summary("La solicitud carece de credenciales de autenticación válidas")
                .timestamp(LocalDateTime.now())
                .build();
        
        model.addAttribute("error", errorResponse.getError());
        model.addAttribute("message", errorResponse.getMessage());
        model.addAttribute("summary", errorResponse.getSummary());
        model.addAttribute("timestamp", errorResponse.getTimestamp());
        
        return "error/NoAutorizado";
    }

    @GetMapping("/403")
    public String handleForbidden(Model model) {
        ErrorHandlerResponse errorResponse = ErrorHandlerResponse.builder()
                .error("Acceso Denegado")
                .message("No tienes permisos suficientes para acceder a este recurso")
                .summary("El servidor ha entendido la solicitud, pero la rechaza por falta de permisos")
                .timestamp(LocalDateTime.now())
                .build();

        model.addAttribute("error", errorResponse.getError());
        model.addAttribute("message", errorResponse.getMessage());
        model.addAttribute("summary", errorResponse.getSummary());
        model.addAttribute("timestamp", errorResponse.getTimestamp());
        return "error/AccesoDenegado";
    }
}