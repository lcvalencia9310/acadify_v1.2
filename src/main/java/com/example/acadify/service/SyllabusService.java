package com.example.acadify.service;

import com.example.acadify.model.ResultadoAprendizaje;
import com.example.acadify.model.Syllabus;
import com.example.acadify.repository.ResultadoAprendizajeRepository;
import com.example.acadify.repository.SyllabusRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SyllabusService {

    private final SyllabusRepository syllabusRepository;
    private final ResultadoAprendizajeRepository resultadoAprendizajeRepository;

    public SyllabusService(SyllabusRepository syllabusRepository,
                            ResultadoAprendizajeRepository resultadoAprendizajeRepository) {
        this.syllabusRepository = syllabusRepository;
        this.resultadoAprendizajeRepository = resultadoAprendizajeRepository;
    }

    public List<Syllabus> obtenerTodos() {
        return syllabusRepository.findAll();
    }

    public Syllabus obtenerPorId(Long id) {
        return syllabusRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Syllabus no encontrado con ID: " + id));
    }

    public Syllabus guardarSyllabus(Syllabus syllabus) {
        syllabus.setResultadosCubiertos(resolverResultadosCubiertos(syllabus));
        return syllabusRepository.save(syllabus);
    }

    public Syllabus actualizarSyllabus(Long id, Syllabus syllabusActualizado) {
        Optional<Syllabus> syllabusExistente = syllabusRepository.findById(id);

        if (syllabusExistente.isPresent()) {
            Syllabus syllabus = syllabusExistente.get();
            syllabus.setDescripcion(syllabusActualizado.getDescripcion());
            syllabus.setAsignatura(syllabusActualizado.getAsignatura());
            syllabus.setResultadosCubiertos(resolverResultadosCubiertos(syllabusActualizado));
            return syllabusRepository.save(syllabus);
        } else {
            throw new RuntimeException("Syllabus no encontrado con ID: " + id);
        }
    }

    /**
     * Carga los Resultado de Aprendizaje reales a partir de los ids que llegan en el
     * body, y valida que cada uno pertenezca a la misma Asignatura del syllabus —
     * evita asociar un syllabus con resultados de otra asignatura.
     */
    private List<ResultadoAprendizaje> resolverResultadosCubiertos(Syllabus syllabus) {
        List<ResultadoAprendizaje> solicitados = syllabus.getResultadosCubiertos();
        if (solicitados == null || solicitados.isEmpty()) {
            return new ArrayList<>();
        }

        Long idAsignaturaSyllabus = syllabus.getAsignatura() != null ? syllabus.getAsignatura().getIdAsignatura() : null;
        List<ResultadoAprendizaje> resueltos = new ArrayList<>();

        for (ResultadoAprendizaje solicitado : solicitados) {
            ResultadoAprendizaje resultado = resultadoAprendizajeRepository.findById(solicitado.getIdResultado())
                    .orElseThrow(() -> new RuntimeException(
                            "Resultado de aprendizaje no encontrado con ID: " + solicitado.getIdResultado()));

            Long idAsignaturaResultado = resultado.getAsignatura().getIdAsignatura();
            if (idAsignaturaSyllabus == null || !idAsignaturaSyllabus.equals(idAsignaturaResultado)) {
                throw new RuntimeException(
                        "El resultado de aprendizaje " + resultado.getIdResultado()
                                + " no pertenece a la asignatura de este syllabus");
            }
            resueltos.add(resultado);
        }
        return resueltos;
    }

    public void eliminarSyllabus(Long id) {
        if (syllabusRepository.existsById(id)) {
            syllabusRepository.deleteById(id);
        } else {
            throw new RuntimeException("Syllabus no encontrado con ID: " + id);
        }
    }
}
