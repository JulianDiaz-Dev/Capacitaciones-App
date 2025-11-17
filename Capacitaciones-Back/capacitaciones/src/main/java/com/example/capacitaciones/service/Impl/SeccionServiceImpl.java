package com.example.capacitaciones.service.Impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.capacitaciones.DTOs.SeccionDTO;
import com.example.capacitaciones.model.Capacitacion;
import com.example.capacitaciones.model.Seccion;
import com.example.capacitaciones.repository.CapacitacionRepository;
import com.example.capacitaciones.repository.SeccionRepository;
import com.example.capacitaciones.service.SeccionService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SeccionServiceImpl implements SeccionService {

    private final SeccionRepository seccionRepository;
    private final CapacitacionRepository capacitacionRepository;

    @Override
    public Seccion save(SeccionDTO dto) {
        boolean esNuevo = dto.getIdSeccion() == null;
            
        Capacitacion capacitacion = capacitacionRepository
                .findById(dto.getIdCapacitacion())
                .orElseThrow(() -> new RuntimeException("Capacitacion no encontrada"));

        Seccion seccion = new Seccion();
        seccion.setIdSeccion(dto.getIdSeccion());
        seccion.setTitulo(dto.getTitulo());
        seccion.setOrden(dto.getOrden());
        seccion.setCapacitacion(capacitacion);
        if (!esNuevo) {
            Optional<Seccion> seccionExistente = seccionRepository.findById(dto.getIdSeccion());
            seccion.setEntradas(seccionExistente.get().getEntradas());
        }

        return seccionRepository.save(seccion);
    }

    @Override
    public List<SeccionDTO> findAllDTOByIdCapacitacion(Long idCapacitacion) {
        return seccionRepository.findAllDTOByIdCapacitacion(idCapacitacion);
    }

    @Override
    public void delete(Long idSeccion) {
        Seccion seccion = seccionRepository.findById(idSeccion)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        seccionRepository.delete(seccion);
    }

}
