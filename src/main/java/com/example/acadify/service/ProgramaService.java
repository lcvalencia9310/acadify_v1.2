package com.example.acadify.service;

import com.example.acadify.exception.RecursoNoEncontradoException;
import com.example.acadify.model.Programa;
import com.example.acadify.repository.ProgramaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProgramaService {

    private final ProgramaRepository programaRepository;

    public ProgramaService(ProgramaRepository programaRepository) {
        this.programaRepository = programaRepository;
    }

    public List<Programa> obtenerTodos() {
        return programaRepository.findAll();
    }

    public Programa obtenerPorId(Long id) {
        return programaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Programa no encontrado con ID: " + id));
    }

    public Programa guardarPrograma(Programa programa) {
        return programaRepository.save(programa);
    }

    public Programa actualizarPrograma(Long id, Programa programaActualizado) {
        Optional<Programa> programaExistente = programaRepository.findById(id);

        if (programaExistente.isPresent()) {
            Programa programa = programaExistente.get();
            programa.setCodigoPrograma(programaActualizado.getCodigoPrograma());
            programa.setNombrePrograma(programaActualizado.getNombrePrograma());
            programa.setActivo(programaActualizado.getActivo());
            return programaRepository.save(programa);
        } else {
            throw new RecursoNoEncontradoException("Programa no encontrado con ID: " + id);
        }
    }

    public void eliminarPrograma(Long id) {
        if (programaRepository.existsById(id)) {
            programaRepository.deleteById(id);
        } else {
            throw new RecursoNoEncontradoException("Programa no encontrado con ID: " + id);
        }
    }
}
