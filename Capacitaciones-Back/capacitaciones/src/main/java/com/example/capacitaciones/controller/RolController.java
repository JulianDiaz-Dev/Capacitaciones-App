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

import com.example.capacitaciones.model.Rol;
import com.example.capacitaciones.service.RolService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/rol")
@CrossOrigin("*")
@RequiredArgsConstructor
public class RolController {
    private final RolService rolService;

    @GetMapping("")
    public ResponseEntity<?> buscarTodosRol() {
        try {
            List<Rol> list = rolService.findAll();
            return ResponseEntity.ok(list);
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<Rol> GuardaRol(@RequestBody Rol rol) {
        Rol rolResponse = rolService.save(rol);
        return ResponseEntity.status(HttpStatus.CREATED).body(rolResponse);
    }

    @DeleteMapping("/{idRol}")
    public ResponseEntity<Rol> EliminarRol(@PathVariable Long idRol) {
        rolService.delete(idRol);
        return ResponseEntity.noContent().build();
    }
}
