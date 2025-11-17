package com.example.capacitaciones.service;

import java.util.List;

import com.example.capacitaciones.DTOs.UsuarioCapacitacionDTO;
import com.example.capacitaciones.model.UsuarioCapacitacion;

public interface UsuarioCapacitacionService {
    List<UsuarioCapacitacion> save(UsuarioCapacitacionDTO usuarioCapacitacionDTO);
    void eliminar (Long idUsuario, Long idCapacitacion);
}
