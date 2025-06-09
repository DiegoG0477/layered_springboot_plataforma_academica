package com.hitss.academica.repositories;

import com.hitss.academica.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // Para buscar un usuario por su email durante el login
    Optional<Usuario> findByEmail(String email);

    // Para comprobar si ya existe un email al registrar un nuevo usuario
    boolean existsByEmail(String email);
}