package com.example.capacitaciones.service.Impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.capacitaciones.DTOs.CapacitacionDTO;
import com.example.capacitaciones.model.Capacitacion;
import com.example.capacitaciones.repository.CapacitacionRepository;
import com.example.capacitaciones.repository.UsuarioCapacitacionRepository;
import com.example.capacitaciones.service.CapacitacionService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CapacitacionServiceImpl implements CapacitacionService {

    private final CapacitacionRepository capacitacionRepository;
    private final UsuarioCapacitacionRepository usuarioCapacitacionRepository;

    @Override
    public Capacitacion save(Capacitacion capacitacion) {
        boolean esNuevo = capacitacion.getIdCapacitacion() == null;
        
        if (esNuevo) {
            capacitacion.setFechaCreacion(LocalDateTime.now());
        }else{
            Optional<Capacitacion> capacitacionExistente = capacitacionRepository.findById(capacitacion.getIdCapacitacion());
            capacitacion.setFechaCreacion(capacitacionExistente.get().getFechaCreacion());
            capacitacion.setSecciones(capacitacionExistente.get().getSecciones());
        }

        return capacitacionRepository.save(capacitacion);
    }

    @Override
    public List<CapacitacionDTO> findAllDTO() {
        return capacitacionRepository.findAllDTO();
    }

    @Override
    public void delete(Long idCapacitacion) {
        Capacitacion capacitacion = capacitacionRepository.findById(idCapacitacion)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        capacitacionRepository.delete(capacitacion);
    }

    @Override
    public Optional<Capacitacion> findById(Long idCapacitacion) {
        return capacitacionRepository.findById(idCapacitacion);
        
    }
    @Override
    public List<CapacitacionDTO> buscarTodosCapacitacionesPorUsuario(Long idUsuario) {
        return usuarioCapacitacionRepository
            .findByUsuarioIdUsuario(idUsuario)
            .stream()
            .map(uc -> new CapacitacionDTO(
                    uc.getCapacitacion().getIdCapacitacion(),
                    uc.getCapacitacion().getTitulo(),
                    uc.getCapacitacion().getDescripcion(),
                    uc.getCapacitacion().getFechaCreacion(),
                    uc.getProgreso()
            ))
            .collect(Collectors.toList());
        
    }
    
    public List<Capacitacion> obtenerCapacitacionesPorIds(List<Long> idCapacitaciones) {
        List<Capacitacion> capacitaciones = capacitacionRepository.findAllById(idCapacitaciones);

        if (capacitaciones.size() != idCapacitaciones.size()) {
            throw new RuntimeException("Algunas capacitaciones no se encontraron");
        }

        return capacitaciones;
    }

}
