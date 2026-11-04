package com.example.acadify.controller;

import com.example.acadify.model.ResultadoAprendizaje;
import com.example.acadify.service.ResultadoAprendizajeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/resultados-aprendizaje")
public class ResultadoAprendizajeController {

    private final ResultadoAprendizajeService resultadoAprendizajeService;

    public ResultadoAprendizajeController(ResultadoAprendizajeService resultadoAprendizajeService) {
        this.resultadoAprendizajeService = resultadoAprendizajeService;
    }

    @GetMapping
    public List<ResultadoAprendizaje> obtenerResultados() {
        return resultadoAprendizajeService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public ResultadoAprendizaje obtenerResultadoPorId(@PathVariable Long id) {
        return resultadoAprendizajeService.obtenerPorId(id);
    }

    @PostMapping
    public ResultadoAprendizaje crearResultado(@RequestBody ResultadoAprendizaje resultado) {
        return resultadoAprendizajeService.guardarResultado(resultado);
    }

    @PutMapping("/{id}")
    public ResultadoAprendizaje actualizarResultado(@PathVariable Long id, @RequestBody ResultadoAprendizaje resultado) {
        return resultadoAprendizajeService.actualizarResultado(id, resultado);
    }

    @DeleteMapping("/{id}")
    public void eliminarResultado(@PathVariable Long id) {
        resultadoAprendizajeService.eliminarResultado(id);
    }
}
