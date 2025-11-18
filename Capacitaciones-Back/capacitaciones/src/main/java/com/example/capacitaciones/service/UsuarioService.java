package com.example.capacitaciones.service;

import java.util.List;

import com.example.capacitaciones.DTOs.UsuarioDTO;
import com.example.capacitaciones.model.Usuario;

public interface UsuarioService {
    UsuarioDTO save(Usuario usuario);
    List<UsuarioDTO> findAll();
    void delete(Long idUsuario);
    Usuario findByEmail(String email);
    List<UsuarioDTO> findAllByCapacitacion(Long idCapacitacion);
    Usuario findById(Long idUsuario);
}
