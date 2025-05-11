package com.TickTock.TickTock.security.domain.services;

import com.TickTock.TickTock.security.application.dtos.request.AuthRequest;
import com.TickTock.TickTock.security.application.dtos.request.RefreshTokenRequest;
import com.TickTock.TickTock.security.application.dtos.response.AuthResponse;
import com.TickTock.TickTock.security.domain.entities.CredentialEntity;
import com.TickTock.TickTock.security.domain.repositories.CredentialRespository;
import com.TickTock.TickTock.security.infrastructure.jwt.JwtService;
import com.TickTock.TickTock.shared.infrastructure.utils.Role;
import com.TickTock.TickTock.user.application.dtos.request.UserRequest;
import com.TickTock.TickTock.user.domain.entities.UserEntity;
import com.TickTock.TickTock.user.domain.repositories.UserRepository;
import com.TickTock.TickTock.user.domain.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {
    
    private final CredentialRespository credentialRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;


    @Autowired
    public AuthService(CredentialRespository credentialRepository, JwtService jwtService, AuthenticationManager authenticationManager, UserRepository userRepository, PasswordEncoder passwordEncoder, UserService userService) {
        this.credentialRepository = credentialRepository;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    public AuthResponse authenticate(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        var credential = credentialRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return AuthResponse.builder()
                .token(jwtService.generateToken(credential))
                .build();
    }

    public AuthResponse refreshToken(RefreshTokenRequest request) {
        String oldToken = request.getRefreshToken();
        String userEmail = jwtService.extractUsername(oldToken);

        if (userEmail == null) {
            throw new RuntimeException("Token inválido");
        }

        var credential = credentialRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Generar nuevo token
        return AuthResponse.builder()
                .token(jwtService.generateToken(credential))
                .build();
    }

    @Transactional
    public AuthResponse register(UserRequest request) {
        // Verificar si el email ya existe
        if (credentialRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("El email ya está registrado");
        }

        // Crear el usuario y sus credenciales en una sola transacción
        UserEntity user = UserEntity.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .bornDate(request.getBornDate())
                .status(true)
                .build();

        CredentialEntity credential = CredentialEntity.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .user(user)
                .build();

        user.setCredential(credential);

        // Guardar usuario y credenciales
        userRepository.save(user);

        // Generar tokens
        String jwt = jwtService.generateToken(credential);
        String refreshToken = jwtService.generateRefreshToken(credential);

        return AuthResponse.builder()
                .token(jwt)
                .build();
    }



}