package com.hitss.academica.repositories;

import com.hitss.academica.entities.PeriodoLectivo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PeriodoLectivoRepository extends JpaRepository<PeriodoLectivo, Long> {
}