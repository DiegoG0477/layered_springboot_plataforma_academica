package com.hitss.academica.repositories;

import com.hitss.academica.dto.reporte.PromedioAsignaturaDTO;
import com.hitss.academica.entities.Nota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface NotaRepository extends JpaRepository<Nota, Long> {
    List<Nota> findByEstudianteId(Long estudianteId);
    List<Nota> findByAsignaturaId(Long asignaturaId);

    // Esta consulta construye los DTOs directamente. Es la forma más robusta.
    @Query("SELECT new com.hitss.academica.dto.reporte.PromedioAsignaturaDTO(" +
           "   c.nombre, " +
           "   a.id, " +
           "   a.nombre, " +
           "   AVG(n.valor)) " +
           "FROM Nota n " +
           "JOIN n.asignatura a " +
           "JOIN a.curso c " +
           "WHERE n.deleted = false " +
           "GROUP BY c.nombre, a.id, a.nombre " +
           "ORDER BY c.nombre, a.nombre")
    List<PromedioAsignaturaDTO> findPromedioNotasPorAsignatura();
}