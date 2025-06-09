package com.hitss.academica.repositories;

import com.hitss.academica.entities.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaterialRepository extends JpaRepository<Material, Long> {
    // Para buscar todos los materiales de una asignatura
    List<Material> findByAsignaturaId(Long asignaturaId);
}