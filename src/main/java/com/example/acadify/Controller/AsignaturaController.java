package com.example.acadify.controller;

import com.example.acadify.model.Asignatura;
import com.example.acadify.service.AsignaturaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/asignaturas")

public class AsignaturaController {

    private final AsignaturaService asignaturaService;

    public AsignaturaController(AsignaturaService asignaturaService) {
        this.asignaturaService = asignaturaService;
    }

    @GetMapping
    public List<Asignatura> obtenerAsignaturas() {
        return asignaturaService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public Asignatura obtenerAsignaturaPorId(@PathVariable Long id) {
        return asignaturaService.obtenerPorId(id);
    }

    @PostMapping
    public Asignatura crearAsignatura(@RequestBody Asignatura asignatura) {
        return asignaturaService.guardarAsignatura(asignatura);
    }

    @PutMapping("/{id}")
    public Asignatura actualizarAsignatura(@PathVariable Long id, @RequestBody Asignatura asignatura) {
        return asignaturaService.actualizarAsignatura(id, asignatura);
    }

    @DeleteMapping("/{id}")
    public void eliminarAsignatura(@PathVariable Long id) {
        asignaturaService.eliminarAsignatura(id);
    }
}
