package com.example.capacitaciones.DTOs;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CapacitacionDTO {
    private Long idCapacitacion;
    private String titulo;
    private String descripcion;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime fechaCreacion;
}
