package com.example.capacitaciones.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.capacitaciones.DTOs.EntradaDTO;
import com.example.capacitaciones.model.Entrada;

@Repository
public interface EntradaRepository extends JpaRepository<Entrada, Long> {

    @Query("""
           SELECT new com.example.capacitaciones.DTOs.EntradaDTO(
               e.id, e.titulo, e.contenido, e.archivoUrl, e.orden, null
           )
           FROM Entrada e
           WHERE e.seccion.idSeccion = :idSeccion
           """)
    List<EntradaDTO> findAllDTOByIdSeccion(@Param("idSeccion") Long idSeccion);
}
