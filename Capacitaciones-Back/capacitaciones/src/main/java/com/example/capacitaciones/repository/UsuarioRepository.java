package com.example.capacitaciones.repository;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.capacitaciones.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

    Optional<Usuario> findByEmail(String email);

    @Query("SELECT u FROM Usuario u JOIN UsuarioCapacitacion uc ON u.id = uc.usuario.id WHERE uc.capacitacion.idCapacitacion = :idCapacitacion")
    List<Usuario> findAllByCapacitacion(@Param("idCapacitacion") Long idCapacitacion);


}
