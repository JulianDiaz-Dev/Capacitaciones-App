package com.example.capacitaciones.service;

import java.util.List;

import com.example.capacitaciones.DTOs.SeccionDTO;
import com.example.capacitaciones.model.Seccion;

public interface SeccionService {
    Seccion save(SeccionDTO seccionDTO);

    List<SeccionDTO> findAllDTOByIdCapacitacion(Long idCapacitacion);

    void delete(Long idSeccion);
}
