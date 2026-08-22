package com.example.acadify.service;

import com.example.acadify.model.ResultadoAprendizaje;
import com.example.acadify.repository.ResultadoAprendizajeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ResultadoAprendizajeService {

    private final ResultadoAprendizajeRepository resultadoAprendizajeRepository;

    public ResultadoAprendizajeService(ResultadoAprendizajeRepository resultadoAprendizajeRepository) {
        this.resultadoAprendizajeRepository = resultadoAprendizajeRepository;
    }

    public List<ResultadoAprendizaje> obtenerTodos() {
        return resultadoAprendizajeRepository.findAll();
    }

    public ResultadoAprendizaje obtenerPorId(Long id) {
        return resultadoAprendizajeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Resultado de aprendizaje no encontrado con ID: " + id));
    }

    public ResultadoAprendizaje guardarResultado(ResultadoAprendizaje resultado) {
        return resultadoAprendizajeRepository.save(resultado);
    }

    public ResultadoAprendizaje actualizarResultado(Long id, ResultadoAprendizaje resultadoActualizado) {
        Optional<ResultadoAprendizaje> resultadoExistente = resultadoAprendizajeRepository.findById(id);

        if (resultadoExistente.isPresent()) {
            ResultadoAprendizaje resultado = resultadoExistente.get();
            resultado.setDescripcion(resultadoActualizado.getDescripcion());
            resultado.setAsignatura(resultadoActualizado.getAsignatura());
            return resultadoAprendizajeRepository.save(resultado);
        } else {
            throw new RuntimeException("Resultado de aprendizaje no encontrado con ID: " + id);
        }
    }

    public void eliminarResultado(Long id) {
        if (resultadoAprendizajeRepository.existsById(id)) {
            resultadoAprendizajeRepository.deleteById(id);
        } else {
            throw new RuntimeException("Resultado de aprendizaje no encontrado con ID: " + id);
        }
    }
}
