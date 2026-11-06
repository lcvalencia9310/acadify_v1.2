package com.example.acadify.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

/**
 * Traduce las excepciones de los controladores a JSON con código + mensaje,
 * en vez de dejar que Spring devuelva el stack trace crudo (lo que exponía
 * la implementación interna y no daba al frontend nada legible que mostrar).
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(RecursoNoEncontradoException.class)
    public ResponseEntity<Map<String, String>> manejarNoEncontrado(RecursoNoEncontradoException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
    }

    @ExceptionHandler(SolicitudInvalidaException.class)
    public ResponseEntity<Map<String, String>> manejarSolicitudInvalida(SolicitudInvalidaException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, String>> manejarViolacionDeIntegridad(DataIntegrityViolationException e) {
        String causaRaiz = e.getRootCause() != null ? e.getRootCause().getMessage() : e.getMessage();
        log.warn("Violación de integridad de datos: {}", causaRaiz);

        String mensaje;
        if (causaRaiz != null && causaRaiz.toLowerCase().contains("foreign key")) {
            mensaje = "No se puede eliminar: este registro está siendo usado por otro (por ejemplo, un syllabus u otra asignatura).";
        } else if (causaRaiz != null && causaRaiz.toLowerCase().contains("duplicate")) {
            mensaje = "Ya existe un registro con ese mismo código.";
        } else {
            mensaje = "La operación no se pudo completar: el dato entra en conflicto con un registro existente.";
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error", mensaje));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> manejarErrorInesperado(Exception e) {
        log.error("Error inesperado", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "Ocurrió un error inesperado en el servidor."));
    }
}
