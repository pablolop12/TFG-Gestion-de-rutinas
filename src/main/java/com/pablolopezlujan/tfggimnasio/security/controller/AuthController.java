package com.pablolopezlujan.tfggimnasio.security.controller;

import com.pablolopezlujan.tfggimnasio.security.dto.LoginCredential;
import com.pablolopezlujan.tfggimnasio.security.dto.RegisterRequest;
import com.pablolopezlujan.tfggimnasio.security.dto.TokenDto;
import com.pablolopezlujan.tfggimnasio.security.service.AuthService;
import com.pablolopezlujan.tfggimnasio.service.PasswordResetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("api/v1/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public TokenDto login(@RequestBody LoginCredential request) {
        logger.info("POST /auth/login usuario:" + request.getEmail() + " contraseña:" + request.getPassword());
        return authService.login(request);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public RegisterRequest register(@RequestBody RegisterRequest request) {
        logger.info("POST /auth/register usuario:" + request.getEmail() + " contraseña:" + request.getPassword());
        return authService.register(request);
    }

    @PostMapping("/register-admin")
    @ResponseStatus(HttpStatus.CREATED)
    public RegisterRequest registerAdmin(@RequestBody RegisterRequest request) {
        logger.info("POST /admin/register");
        return authService.registerAdmin(request);
    }

    // Endpoint para enviar correo de recuperación de contraseña
    @PostMapping("/forgot-password")
    @ResponseStatus(HttpStatus.OK)
    public void forgotPassword(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        authService.sendPasswordResetEmail(email);
    }

    // Endpoint para restablecer la contraseña
    @PostMapping("/reset-password")
    @ResponseStatus(HttpStatus.OK)
    public void resetPassword(@RequestBody Map<String, String> body) {
        String token = body.get("token");
        String newPassword = body.get("newPassword");
        authService.resetPassword(token, newPassword);
    }
}

