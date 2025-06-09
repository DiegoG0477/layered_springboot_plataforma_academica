package com.hitss.academica.repositories;

import com.hitss.academica.entities.Asignatura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AsignaturaRepository extends JpaRepository<Asignatura, Long> {
    // Para buscar las asignaturas de un profesor específico
    List<Asignatura> findByProfesorId(Long profesorId);

    // Para buscar las asignaturas de un curso específico
    List<Asignatura> findByCursoId(Long cursoId);
}