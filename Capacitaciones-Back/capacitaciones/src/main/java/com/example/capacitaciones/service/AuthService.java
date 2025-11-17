package com.example.capacitaciones.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.capacitaciones.DTOs.LoginRequest;
import com.example.capacitaciones.DTOs.LoginResponse;
import com.example.capacitaciones.model.Usuario;
import com.example.capacitaciones.repository.UsuarioRepository;
import com.example.capacitaciones.security.JwtUtil;

import lombok.AllArgsConstructor;
@Service
@AllArgsConstructor
public class AuthService {
    private final UsuarioRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;    

    public LoginResponse login(LoginRequest request) {

        Usuario user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Contraseña incorrecta");
        }

        String token = jwtUtil.generateToken(user);

        return new LoginResponse(token);
    }

}
