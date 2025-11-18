package com.example.capacitaciones.service.Impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.example.capacitaciones.DTOs.UsuarioCapacitacionDTO;
import com.example.capacitaciones.model.Capacitacion;
import com.example.capacitaciones.model.Usuario;
import com.example.capacitaciones.model.UsuarioCapacitacion;
import com.example.capacitaciones.repository.UsuarioCapacitacionRepository;
import com.example.capacitaciones.repository.UsuarioRepository;
import com.example.capacitaciones.service.CapacitacionService;
import com.example.capacitaciones.service.EnvioService;
import com.example.capacitaciones.service.UsuarioCapacitacionService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioCapacitacionServiceImpl implements UsuarioCapacitacionService {
    private final UsuarioCapacitacionRepository usuarioCapacitacionRepository;
    private final UsuarioRepository usuarioRepository;
    private final CapacitacionService capacitacionService;
    private final EnvioService envioService;

    @Override
    public List<UsuarioCapacitacion> save(UsuarioCapacitacionDTO usuarioCapacitacionDTO) {
        boolean esNuevo = usuarioCapacitacionDTO.getIdUsuarioCapacitacion() == null;
        Usuario usuario = usuarioRepository.findById(usuarioCapacitacionDTO.getIdUsuario())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        List<Capacitacion> capacitaciones = capacitacionService
                .obtenerCapacitacionesPorIds(usuarioCapacitacionDTO.getIdCapacitaciones());
        List<UsuarioCapacitacion> asignaciones = new ArrayList<>();
        for (Capacitacion capacitacion : capacitaciones) {
            boolean Asignado = usuarioCapacitacionRepository.existsByUsuarioAndCapacitacion(usuario, capacitacion);
            if (Asignado)
                continue;
            UsuarioCapacitacion usuarioCapacitacion = new UsuarioCapacitacion();
            usuarioCapacitacion.setUsuario(usuario);
            usuarioCapacitacion.setCapacitacion(capacitacion);
            usuarioCapacitacion.setProgreso(usuarioCapacitacionDTO.getProgreso());
            if (esNuevo) {
                usuarioCapacitacion.setFechaAsignacion(LocalDateTime.now());
            }
            asignaciones.add(usuarioCapacitacionRepository.save(usuarioCapacitacion));
        }
        if (!asignaciones.isEmpty()) {
            String nombres = asignaciones.stream().map(uc -> uc.getCapacitacion().getTitulo())
                    .collect(Collectors.joining(", "));
            String mensaje = "Hola " + usuario.getNombre() + ", se te han asignado las capacitaciones: " + nombres;
            envioService.enviarCorreo(usuario.getEmail(), "Nueva asignación de capacitaciones", mensaje);
        }
        return asignaciones;
    }

    @Override
    public void eliminar(Long idUsuario, Long idCapacitacion) {
        UsuarioCapacitacion asignacion = usuarioCapacitacionRepository
                .findByUsuario_IdUsuarioAndCapacitacion_IdCapacitacion(idUsuario, idCapacitacion)
                .orElseThrow(() -> new RuntimeException("Asignación no encontrada"));
        usuarioCapacitacionRepository.delete(asignacion);
    }

    @Override
    public List<UsuarioCapacitacionDTO> guardarProgreso(UsuarioCapacitacionDTO dto) {
        List<UsuarioCapacitacionDTO> resultados = new ArrayList<>();

        for (Long idCapacitacion : dto.getIdCapacitaciones()) {
            Optional<UsuarioCapacitacion> optional = usuarioCapacitacionRepository
                    .findByUsuario_IdUsuarioAndCapacitacion_IdCapacitacion(dto.getIdUsuario(), idCapacitacion);
            if (optional.isPresent()) {
                UsuarioCapacitacion entidad = optional.get();
                entidad.setProgreso(dto.getProgreso());
                if (dto.getProgreso() >= 100 && entidad.getFechaFinalizacion() == null) {
                    Usuario usuario = entidad.getUsuario();
                    usuario.setInsignias(usuario.getInsignias() + 1);
                    usuarioRepository.save(usuario);
                }
                if (dto.getProgreso() >= 100) {
                    entidad.setFechaFinalizacion(LocalDateTime.now());
                }
                UsuarioCapacitacion guardado = usuarioCapacitacionRepository.save(entidad);
                UsuarioCapacitacionDTO resultado = new UsuarioCapacitacionDTO();
                resultado.setIdUsuario(guardado.getUsuario().getIdUsuario());
                List<Long> idCapList = new ArrayList<>();
                idCapList.add(guardado.getCapacitacion().getIdCapacitacion());
                resultado.setIdCapacitaciones(idCapList);
                resultado.setProgreso(guardado.getProgreso());
                resultados.add(resultado);
            }
        }
        return resultados;
    }
}