package com.example.capacitaciones.service;

import java.util.List;

import com.example.capacitaciones.model.TipoEntrada;

public interface TipoEntradaService {
    TipoEntrada save(TipoEntrada tipoEntrada);

    List<TipoEntrada> findAll();

    void delete(Long tipoEntrada);
}
