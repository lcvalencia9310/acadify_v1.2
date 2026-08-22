package com.example.acadify.service;

import com.example.acadify.model.Asignatura;
import com.example.acadify.repository.AsignaturaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AsignaturaService {

    private final AsignaturaRepository asignaturaRepository;

    public AsignaturaService(AsignaturaRepository asignaturaRepository) {
        this.asignaturaRepository = asignaturaRepository;
    }

    public List<Asignatura> obtenerTodos() {
        return asignaturaRepository.findAll();
    }

    public Asignatura obtenerPorId(Long id) {
        return asignaturaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Asignatura no encontrada con ID: " + id));
    }

    public Asignatura guardarAsignatura(Asignatura asignatura) {
        return asignaturaRepository.save(asignatura);
    }

    public Asignatura actualizarAsignatura(Long id, Asignatura asignaturaActualizada) {
        Optional<Asignatura> asignaturaExistente = asignaturaRepository.findById(id);

        if (asignaturaExistente.isPresent()) {
            Asignatura asignatura = asignaturaExistente.get();
            asignatura.setCodigoAsignatura(asignaturaActualizada.getCodigoAsignatura());
            asignatura.setNombreAsignatura(asignaturaActualizada.getNombreAsignatura());
            asignatura.setCreditos(asignaturaActualizada.getCreditos());
            asignatura.setIntensidadHoraria(asignaturaActualizada.getIntensidadHoraria());
            asignatura.setPrograma(asignaturaActualizada.getPrograma());
            return asignaturaRepository.save(asignatura);
        } else {
            throw new RuntimeException("Asignatura no encontrada con ID: " + id);
        }
    }

    public void eliminarAsignatura(Long id) {
        if (asignaturaRepository.existsById(id)) {
            asignaturaRepository.deleteById(id);
        } else {
            throw new RuntimeException("Asignatura no encontrada con ID: " + id);
        }
    }
}
