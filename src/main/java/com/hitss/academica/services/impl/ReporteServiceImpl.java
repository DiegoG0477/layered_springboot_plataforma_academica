package com.hitss.academica.services.impl;

import com.hitss.academica.dto.reporte.EstudianteReporteDTO;
import com.hitss.academica.dto.reporte.HistorialAcademicoDTO;
import com.hitss.academica.dto.reporte.PromedioAsignaturaDTO;
import com.hitss.academica.dto.reporte.ReporteFinalDTO;
import com.hitss.academica.entities.Curso;
import com.hitss.academica.entities.Estudiante;
import com.hitss.academica.mappers.NotaMapper;
import com.hitss.academica.repositories.CursoRepository;
import com.hitss.academica.repositories.EstudianteRepository;
import com.hitss.academica.repositories.NotaRepository;
import com.hitss.academica.services.ReporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReporteServiceImpl implements ReporteService {

    @Autowired
    private NotaRepository notaRepository;
    @Autowired
    private EstudianteRepository estudianteRepository;
    @Autowired
    private CursoRepository cursoRepository;
    @Autowired
    private NotaMapper notaMapper;

    @Override
    @Transactional(readOnly = true)
    public List<PromedioAsignaturaDTO> getPromedioNotasPorAsignatura() {
        // La lógica ahora vive felizmente en la consulta del repositorio.
        // El servicio solo la invoca.
        return notaRepository.findPromedioNotasPorAsignatura();
    }

    @Override
    @Transactional(readOnly = true)
    public HistorialAcademicoDTO getHistorialEstudiante(Long estudianteId) {
        Estudiante estudiante = estudianteRepository.findById(estudianteId)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado con ID: " + estudianteId));

        HistorialAcademicoDTO historial = new HistorialAcademicoDTO();
        historial.setEstudianteId(estudiante.getId());
        historial.setNombreEstudiante(estudiante.getUsuario().getNombre() + " " + estudiante.getUsuario().getApellidoPaterno());
        historial.setCodigoMatricula(estudiante.getCodigoMatricula());
        historial.setCursoActual(estudiante.getCursoActual().getNombre());
        historial.setNotas(notaMapper.notasToNotaResponseDtos(notaRepository.findByEstudianteId(estudianteId)));

        return historial;
    }

    @Override
    @Transactional(readOnly = true)
    public ReporteFinalDTO getReporteFinalCurso(Long cursoId) {
        Curso curso = cursoRepository.findById(cursoId)
                .orElseThrow(() -> new RuntimeException("Curso no encontrado con ID: " + cursoId));

        List<Estudiante> estudiantes = estudianteRepository.findAllByCursoActualId(cursoId);

        ReporteFinalDTO reporteFinal = new ReporteFinalDTO();
        reporteFinal.setCursoId(curso.getId());
        reporteFinal.setNombreCurso(curso.getNombre());
        reporteFinal.setPeriodoLectivo(curso.getPeriodoLectivo().getNombre());

        List<EstudianteReporteDTO> resultados = estudiantes.stream().map(estudiante -> {
            EstudianteReporteDTO estudianteDTO = new EstudianteReporteDTO();
            estudianteDTO.setEstudianteId(estudiante.getId());
            estudianteDTO.setNombreEstudiante(estudiante.getUsuario().getNombre() + " " + estudiante.getUsuario().getApellidoPaterno());

            Map<String, Integer> notasMap = notaRepository.findByEstudianteId(estudiante.getId())
                    .stream()
                    .filter(nota -> nota.getAsignatura().getCurso().getId().equals(cursoId))
                    .collect(Collectors.toMap(
                            nota -> nota.getAsignatura().getNombre(),
                            nota -> nota.getValor(),
                            (valorExistente, nuevoValor) -> nuevoValor
                    ));
            estudianteDTO.setNotasPorAsignatura(notasMap);
            return estudianteDTO;
        }).collect(Collectors.toList());

        reporteFinal.setResultadosEstudiantes(resultados);
        return reporteFinal;
    }
}