package com.hitss.academica.repositories;

import com.hitss.academica.entities.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EstudianteRepository extends JpaRepository<Estudiante, Long> {

    boolean existsByCodigoMatricula(String codigoMatricula);

    Optional<Estudiante> findByCodigoMatricula(String codigoMatricula);

    List<Estudiante> findAllByCursoActualId(Long cursoId);

    @Query("SELECT e FROM Estudiante e WHERE e.cursoActual.id = (SELECT a.curso.id FROM Asignatura a WHERE a.id = :asignaturaId)")
    List<Estudiante> findByAsignaturaId(@Param("asignaturaId") Long asignaturaId);

    Optional<Estudiante> findByUsuario_Email(String email);
}