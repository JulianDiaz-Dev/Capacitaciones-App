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

import com.example.capacitaciones.DTOs.SeccionDTO;
import com.example.capacitaciones.model.Seccion;
import com.example.capacitaciones.service.SeccionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/seccion")
@CrossOrigin("*")
@RequiredArgsConstructor
public class SeccionController {
    private final SeccionService seccionService;

    @GetMapping("/seccionPorCapacitacion/{idCapacitacion}")
    public ResponseEntity<?> buscarTodosSeccionPorCapacitacion(@PathVariable Long idCapacitacion) {
        try {
            List<SeccionDTO> list = seccionService.findAllDTOByIdCapacitacion(idCapacitacion);
            return ResponseEntity.ok(list);
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<Seccion> GuardaSeccion(@RequestBody SeccionDTO seccionDTO) {

        Seccion seccionResponse = seccionService.save(seccionDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(seccionResponse);
    }

    @DeleteMapping("/{idSeccion}")
    public ResponseEntity<Seccion> EliminarSeccion(@PathVariable Long idSeccion) {
        seccionService.delete(idSeccion);
        return ResponseEntity.noContent().build();
    }
}
