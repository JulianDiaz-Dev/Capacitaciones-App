package com.example.capacitaciones.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.capacitaciones.model.TipoEntrada;

@Repository
public interface TipoEntradaRepository extends JpaRepository<TipoEntrada, Long>{

}
