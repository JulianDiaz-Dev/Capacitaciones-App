package com.example.capacitaciones.service;

import java.util.List;

import com.example.capacitaciones.DTOs.EntradaDTO;
import com.example.capacitaciones.model.Entrada;

public interface EntradaService {
    Entrada save(EntradaDTO entradaDTO);

    List<EntradaDTO> findAllDTOByIdSeccion(Long idSeccion);

    void delete(Long identrada);

}
