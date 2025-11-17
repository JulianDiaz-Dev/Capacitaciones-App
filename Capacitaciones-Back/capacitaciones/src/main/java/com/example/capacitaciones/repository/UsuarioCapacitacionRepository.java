package com.example.capacitaciones.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.capacitaciones.model.Capacitacion;
import com.example.capacitaciones.model.Usuario;
import com.example.capacitaciones.model.UsuarioCapacitacion;

@Repository
public interface UsuarioCapacitacionRepository extends JpaRepository<UsuarioCapacitacion, Long> {
    boolean existsByUsuarioAndCapacitacion(Usuario usuario, Capacitacion capacitacion);

    Optional<UsuarioCapacitacion> findByUsuario_IdUsuarioAndCapacitacion_IdCapacitacion(Long idUsuario, Long idCapacitacion);

}
