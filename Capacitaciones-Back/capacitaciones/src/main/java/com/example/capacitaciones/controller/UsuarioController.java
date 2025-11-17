package com.example.capacitaciones.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.capacitaciones.DTOs.UsuarioDTO;
import com.example.capacitaciones.model.Usuario;
import com.example.capacitaciones.service.UsuarioService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/usuario")
@CrossOrigin("*")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @GetMapping("")
    public ResponseEntity<?> buscarTodosUsuarios() {
        try {
            List<UsuarioDTO> list = usuarioService.findAll();
            return ResponseEntity.ok(list);
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<UsuarioDTO> GuardaUsuario(@RequestBody Usuario usuario) {
        UsuarioDTO usuarioDtp = usuarioService.save(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioDtp);
    }

    @DeleteMapping("/{idUsuario}")
    public ResponseEntity<UsuarioDTO> EliminarUsuario(@PathVariable Long idUsuario) {
        usuarioService.delete(idUsuario);
        return ResponseEntity.noContent().build();
    }
}
