package com.example.capacitaciones.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeccionDTO {

    private Long idSeccion;
    private Long idCapacitacion;
    private String titulo;
    private Integer orden = 0;
}
