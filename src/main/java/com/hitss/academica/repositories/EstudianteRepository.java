package com.hitss.academica.repositories;

import com.hitss.academica.entities.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EstudianteRepository extends JpaRepository<Estudiante, Long> {
    // Para comprobar si ya existe un código de matrícula
    boolean existsByCodigoMatricula(String codigoMatricula);

    // Para buscar un estudiante por su código
    Optional<Estudiante> findByCodigoMatricula(String codigoMatricula);

    List<Estudiante> findAllByCursoActualId(Long cursoId);
}