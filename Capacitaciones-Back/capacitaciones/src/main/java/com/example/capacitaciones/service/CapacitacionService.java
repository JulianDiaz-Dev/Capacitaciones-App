package com.example.capacitaciones.service;

import java.util.List;
import java.util.Optional;

import com.example.capacitaciones.DTOs.CapacitacionDTO;
import com.example.capacitaciones.model.Capacitacion;

public interface CapacitacionService {
    Capacitacion save(Capacitacion capacitacion);

    List<CapacitacionDTO> findAllDTO();

    void delete(Long idCapacitacion);
    Optional<Capacitacion> findById(Long idCapacitacion);

    List<Capacitacion> obtenerCapacitacionesPorIds(List<Long> idCapacitaciones);
}
