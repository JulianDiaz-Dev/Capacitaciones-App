package com.example.capacitaciones.service.Impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.capacitaciones.DTOs.UsuarioDTO;
import com.example.capacitaciones.model.Rol;
import com.example.capacitaciones.model.Usuario;
import com.example.capacitaciones.repository.UsuarioRepository;
import com.example.capacitaciones.service.UsuarioService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UsuarioDTO save(Usuario usuario) {

        boolean esNuevo = usuario.getIdUsuario() == null;
        Optional<Usuario> emailExistente = usuarioRepository.findByEmail(usuario.getEmail());
        if (emailExistente.isPresent() && !emailExistente.get().getIdUsuario().equals(usuario.getIdUsuario())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "El email ya está registrado por otro usuario");
        }
        if (esNuevo) {
            if (usuario.getPassword() == null || usuario.getPassword().isBlank()) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "La contraseña es obligatoria al crear un usuario");
            }
            usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        } else {
            Usuario usuarioDB = usuarioRepository.findById(usuario.getIdUsuario())
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND,
                            "Usuario no encontrado"));
            if (usuario.getPassword() == null || usuario.getPassword().isBlank()) {
                usuario.setPassword(usuarioDB.getPassword());
            } else {
                usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
            }

        }
        return toDTO(usuarioRepository.save(usuario));
    }

    @Override
    public List<UsuarioDTO> findAll() {
        return usuarioRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        usuarioRepository.delete(usuario);
    }

    private UsuarioDTO toDTO(Usuario usuario) {
        List<String> nombresRoles = usuario.getRoles().stream()
                .map(Rol::getNombre)
                .collect(Collectors.toList());
        return new UsuarioDTO(usuario.getIdUsuario(),
                usuario.getEmail(), nombresRoles, usuario.getNombre(), usuario.getApellido(), usuario.isActivo(),
                usuario.isContraseniaTemporal());
    }

    @Override
    public Usuario findByEmail(String email) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(email);
        return usuarioOpt.orElse(null);
    }

    @Override
    public List<UsuarioDTO> findAllByCapacitacion(Long idCapacitacion) {
        return usuarioRepository.findAllByCapacitacion(idCapacitacion).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

}
