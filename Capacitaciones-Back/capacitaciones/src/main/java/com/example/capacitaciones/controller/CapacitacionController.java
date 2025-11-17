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

import com.example.capacitaciones.DTOs.CapacitacionDTO;
import com.example.capacitaciones.model.Capacitacion;
import com.example.capacitaciones.service.CapacitacionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/capacitacion")
@CrossOrigin("*")
@RequiredArgsConstructor
public class CapacitacionController {
    private final CapacitacionService capacitacionService;

    @GetMapping("")
    public ResponseEntity<?> buscarTodosCapacitaciones() {
        try {
            List<CapacitacionDTO> list = capacitacionService.findAllDTO();
            return ResponseEntity.ok(list);
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<Capacitacion> GuardaCapacitacion(@RequestBody Capacitacion capacitacion) {
        Capacitacion capacitacionResponse = capacitacionService.save(capacitacion);
        return ResponseEntity.status(HttpStatus.CREATED).body(capacitacionResponse);
    }

    @DeleteMapping("/{idCapacitacion}")
    public ResponseEntity<Capacitacion> EliminarCapacitacion(@PathVariable Long idCapacitacion) {
        capacitacionService.delete(idCapacitacion);
        return ResponseEntity.noContent().build();
    }
}
