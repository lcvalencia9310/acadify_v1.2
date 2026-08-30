package com.example.acadify.controller;

import com.example.acadify.model.Syllabus;
import com.example.acadify.service.SyllabusService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/syllabus")
@CrossOrigin(origins = "https://acadify-frontend-one.vercel.app")
public class SyllabusController {

    private final SyllabusService syllabusService;

    public SyllabusController(SyllabusService syllabusService) {
        this.syllabusService = syllabusService;
    }

    // Obtener todos los syllabus
    @GetMapping
    public List<Syllabus> obtenerSyllabus() {
        return syllabusService.obtenerTodos();
    }

    // Obtener un syllabus por id
    @GetMapping("/{id}")
    public Syllabus obtenerSyllabusPorId(@PathVariable Long id) {
        return syllabusService.obtenerPorId(id);
    }

    // Crear un nuevo syllabus
    @PostMapping
    public Syllabus crearSyllabus(@RequestBody Syllabus syllabus) {
        return syllabusService.guardarSyllabus(syllabus);
    }

    // Actualizar un syllabus existente
    @PutMapping("/{id}")
    public Syllabus actualizarSyllabus(@PathVariable Long id, @RequestBody Syllabus syllabus) {
        return syllabusService.actualizarSyllabus(id, syllabus);
    }

    // Eliminar un syllabus
    @DeleteMapping("/{id}")
    public void eliminarSyllabus(@PathVariable Long id) {
        syllabusService.eliminarSyllabus(id);
    }
}
