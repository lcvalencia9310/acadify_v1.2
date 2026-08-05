package com.example.acadify.service;

import com.example.acadify.model.Syllabus;
import com.example.acadify.repository.SyllabusRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SyllabusService {

    private final SyllabusRepository syllabusRepository;

    public SyllabusService(SyllabusRepository syllabusRepository) {
        this.syllabusRepository = syllabusRepository;
    }

    // Obtener todos los syllabus
    public List<Syllabus> obtenerTodos() {
        return syllabusRepository.findAll();
    }

    // Obtener un syllabus por id
    public Syllabus obtenerPorId(Long id) {
        return syllabusRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Syllabus no encontrado con ID: " + id));
    }

    // Guardar un nuevo syllabus
    public Syllabus guardarSyllabus(Syllabus syllabus) {
        return syllabusRepository.save(syllabus);
    }

    // Actualizar un syllabus existente
    public Syllabus actualizarSyllabus(Long id, Syllabus syllabusActualizado) {
        Optional<Syllabus> syllabusExistente = syllabusRepository.findById(id);

        if (syllabusExistente.isPresent()) {
            Syllabus syllabus = syllabusExistente.get();
            syllabus.setNombreAsignatura(syllabusActualizado.getNombreAsignatura());
            syllabus.setDescripcion(syllabusActualizado.getDescripcion());
            syllabus.setCreditos(syllabusActualizado.getCreditos());
            syllabus.setIntensidadHoraria(syllabusActualizado.getIntensidadHoraria());
            syllabus.setProgramaAcademico(syllabusActualizado.getProgramaAcademico());
            return syllabusRepository.save(syllabus);
        } else {
            throw new RuntimeException("Syllabus no encontrado con ID: " + id);
        }
    }

    // Eliminar un syllabus por id
    public void eliminarSyllabus(Long id) {
        if (syllabusRepository.existsById(id)) {
            syllabusRepository.deleteById(id);
        } else {
            throw new RuntimeException("Syllabus no encontrado con ID: " + id);
        }
    }
}
