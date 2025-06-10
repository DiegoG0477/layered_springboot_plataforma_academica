package com.hitss.academica.repositories;

import com.hitss.academica.entities.Asignatura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AsignaturaRepository extends JpaRepository<Asignatura, Long> {
    List<Asignatura> findByProfesorId(Long profesorId);

    List<Asignatura> findByCursoId(Long cursoId);
}