package com.example.capacitaciones.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.capacitaciones.DTOs.SeccionDTO;
import com.example.capacitaciones.model.Seccion;

@Repository
public interface SeccionRepository extends JpaRepository<Seccion, Long>{
    @Query("""
            SELECT new com.example.capacitaciones.DTOs.SeccionDTO(
                s.idSeccion, null, s.titulo, s.orden
            )FROM Seccion s 
            WHERE s.capacitacion.idCapacitacion = :idCapacitacion
            """)
    List<SeccionDTO> findAllDTOByIdCapacitacion(Long idCapacitacion);
}
