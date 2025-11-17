package com.example.capacitaciones.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Entrada {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne 
    @JoinColumn(name = "idSeccion", nullable = false)
    @JsonBackReference
    private Seccion seccion;

    @ManyToOne
    @JoinColumn(name = "idTipoEntrada", nullable = false)
    private TipoEntrada tipoEntrada;

    @Column(nullable = false)
    private String titulo;

    
    @Column(columnDefinition = "TEXT")
    private String contenido;

    private String archivoUrl;

    private Integer orden = 0;
}
