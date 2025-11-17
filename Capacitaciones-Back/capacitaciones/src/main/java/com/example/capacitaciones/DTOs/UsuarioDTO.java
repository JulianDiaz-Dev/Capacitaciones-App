package com.example.capacitaciones.DTOs;

import java.util.ArrayList;
import java.util.List;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {
    private Long idUsuario;
    private String email;
    private List<String> roles = new ArrayList<>();;
    private String nombre;
    private String apellido;
    private boolean activo = true;
    private boolean contraseniaTemporal = false;
}
