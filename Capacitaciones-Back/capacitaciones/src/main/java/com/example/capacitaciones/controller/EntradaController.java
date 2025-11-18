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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.capacitaciones.DTOs.EntradaDTO;
import com.example.capacitaciones.model.Entrada;
import com.example.capacitaciones.service.EntradaService;
import com.example.capacitaciones.service.MinioService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/entrada")
@CrossOrigin("*")
@RequiredArgsConstructor
public class EntradaController {

    private final EntradaService entradaService;
    private final MinioService minioService;

    @GetMapping("/entradaPorSeccion/{idSeccion}")
    public ResponseEntity<?> buscarTodosEntradaPorSeccion(@PathVariable Long idSeccion) {
        try {
            List<EntradaDTO> list = entradaService.findAllDTOByIdSeccion(idSeccion);
            return ResponseEntity.ok(list);
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<Entrada> GuardaEntrada(@RequestBody EntradaDTO entradaDTO) {
        Entrada entradaResponse = entradaService.save(entradaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(entradaResponse);
    }

    @DeleteMapping("/{idEntrada}")
    public ResponseEntity<Entrada> EliminarEntrada(@PathVariable Long idEntrada) {
        entradaService.delete(idEntrada);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/archivo")
    public ResponseEntity<Map<String, String>> uploadFile(@RequestParam("file") MultipartFile file) {
        String url = minioService.uploadFile(file);
        Map<String, String> response = Map.of("url", url);
        return ResponseEntity.ok(response);
    }
}
