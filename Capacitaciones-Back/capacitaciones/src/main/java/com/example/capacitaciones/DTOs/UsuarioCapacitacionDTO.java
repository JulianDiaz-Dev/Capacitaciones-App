package com.example.capacitaciones.DTOs;

import java.util.List;

import lombok.Data;

@Data
public class UsuarioCapacitacionDTO {
    private Long idUsuarioCapacitacion;
    private Long idUsuario;
    private List<Long> idCapacitaciones;
    private Integer progreso = 0; 
}
