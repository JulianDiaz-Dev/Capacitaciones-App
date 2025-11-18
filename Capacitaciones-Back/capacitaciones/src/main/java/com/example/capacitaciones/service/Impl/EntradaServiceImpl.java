package com.example.capacitaciones.service.Impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.capacitaciones.DTOs.EntradaDTO;
import com.example.capacitaciones.model.Entrada;
import com.example.capacitaciones.model.Seccion;
import com.example.capacitaciones.repository.EntradaRepository;
import com.example.capacitaciones.repository.SeccionRepository;
import com.example.capacitaciones.service.EntradaService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EntradaServiceImpl implements EntradaService{

    private final EntradaRepository entradaRepository;
    private final SeccionRepository seccionRepository;
    @Override
    public Entrada save(EntradaDTO entradaDTO) {
        Seccion seccion = seccionRepository
                .findById(entradaDTO.getIdSeccion())
                .orElseThrow(() -> new RuntimeException("Capacitacion no encontrada"));

        Entrada entrada = new Entrada();
        entrada.setId(entradaDTO.getIdEntrada());
        entrada.setSeccion(seccion);
        entrada.setTitulo(entradaDTO.getTitulo());
        entrada.setContenido(entradaDTO.getContenido());
        entrada.setArchivoUrl(entradaDTO.getArchivoUrl());
        entrada.setOrden(entradaDTO.getOrden());


        return entradaRepository.save(entrada);
    }

    @Override
    public List<EntradaDTO> findAllDTOByIdSeccion(Long idSeccion) {
        return entradaRepository.findAllDTOByIdSeccion(idSeccion);
    }

    @Override
    public void delete(Long idEntrada) {
        Entrada entrada = entradaRepository.findById(idEntrada)
                .orElseThrow(() -> new RuntimeException("entrada no encontrada"));
        entradaRepository.delete(entrada);
    }

}
