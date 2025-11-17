package com.example.capacitaciones.service.Impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.capacitaciones.model.TipoEntrada;
import com.example.capacitaciones.repository.TipoEntradaRepository;
import com.example.capacitaciones.service.TipoEntradaService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TipoEntradaServiceImpl implements TipoEntradaService {

    private final TipoEntradaRepository tipoEntradaRepository;

    @Override
    public TipoEntrada save(TipoEntrada tipoEntrada) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }

    @Override
    public List<TipoEntrada> findAll() {
        return tipoEntradaRepository.findAll();
    }

    @Override
    public void delete(Long tipoEntrada) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

}
