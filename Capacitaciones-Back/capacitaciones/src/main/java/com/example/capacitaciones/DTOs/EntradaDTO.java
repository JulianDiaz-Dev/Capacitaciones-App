package com.example.capacitaciones.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EntradaDTO {
    private Long idEntrada;
    private String titulo;
    private String contenido;
    private String archivoUrl;
    private Integer orden;
    private Long idSeccion;
}
