package com.example.capacitaciones.service;

import java.util.List;

import com.example.capacitaciones.model.Rol;

public interface RolService {
    Rol save(Rol rol);
    List<Rol> findAll();
    void delete(Long idRol);
}
