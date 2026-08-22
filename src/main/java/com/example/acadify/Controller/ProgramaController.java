package com.example.acadify.controller;

import com.example.acadify.model.Programa;
import com.example.acadify.service.ProgramaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/programas")
@CrossOrigin(origins = "http://localhost:4200")
public class ProgramaController {

    private final ProgramaService programaService;

    public ProgramaController(ProgramaService programaService) {
        this.programaService = programaService;
    }

    @GetMapping
    public List<Programa> obtenerProgramas() {
        return programaService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public Programa obtenerProgramaPorId(@PathVariable Long id) {
        return programaService.obtenerPorId(id);
    }

    @PostMapping
    public Programa crearPrograma(@RequestBody Programa programa) {
        return programaService.guardarPrograma(programa);
    }

    @PutMapping("/{id}")
    public Programa actualizarPrograma(@PathVariable Long id, @RequestBody Programa programa) {
        return programaService.actualizarPrograma(id, programa);
    }

    @DeleteMapping("/{id}")
    public void eliminarPrograma(@PathVariable Long id) {
        programaService.eliminarPrograma(id);
    }
}
