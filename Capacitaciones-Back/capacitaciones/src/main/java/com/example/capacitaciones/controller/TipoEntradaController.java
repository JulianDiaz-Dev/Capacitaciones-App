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

import com.example.capacitaciones.model.TipoEntrada;
import com.example.capacitaciones.service.TipoEntradaService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/tipoEntrada")
@CrossOrigin("*")
@RequiredArgsConstructor
public class TipoEntradaController {
    private final TipoEntradaService tipoEntradaService;

    @GetMapping("")
    public ResponseEntity<?> buscarTodosTipoEntrada() {
        try {
            List<TipoEntrada> list = tipoEntradaService.findAll();
            return ResponseEntity.ok(list);
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<TipoEntrada> GuardaTipoEntrada(@RequestBody TipoEntrada tipoEntrada) {
        TipoEntrada tipoEntradaResponse = tipoEntradaService.save(tipoEntrada);
        return ResponseEntity.status(HttpStatus.CREATED).body(tipoEntradaResponse);
    }

    @DeleteMapping("/{idTipoEntrada}")
    public ResponseEntity<TipoEntrada> EliminarTipoEntrada(@PathVariable Long idTipoEntrada) {
        tipoEntradaService.delete(idTipoEntrada);
        return ResponseEntity.noContent().build();
    }
}
