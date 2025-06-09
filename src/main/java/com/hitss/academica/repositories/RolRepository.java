package com.hitss.academica.repositories;

import com.hitss.academica.entities.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolRepository extends JpaRepository<Rol, Long> {
    // Spring Data JPA generará automáticamente la consulta para buscar un rol por su nombre.
    // Lo usaremos en el servicio de usuarios para asignar roles.
    Optional<Rol> findByRol(String rol);
}