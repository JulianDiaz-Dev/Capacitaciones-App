package com.example.capacitaciones.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.capacitaciones.DTOs.CapacitacionDTO;
import com.example.capacitaciones.model.Capacitacion;

@Repository
public interface CapacitacionRepository extends JpaRepository<Capacitacion, Long> {
    @Query("""
            SELECT new com.example.capacitaciones.DTOs.CapacitacionDTO(
                c.idCapacitacion, c.titulo, c.descripcion, c.fechaCreacion, null
            )FROM Capacitacion c
            """)
    List<CapacitacionDTO> findAllDTO();

}
