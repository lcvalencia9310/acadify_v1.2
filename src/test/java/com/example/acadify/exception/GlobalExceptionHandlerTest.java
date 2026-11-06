package com.example.acadify.exception;

import org.junit.jupiter.api.Test;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void recursoNoEncontradoDa404ConElMensaje() {
        ResponseEntity<Map<String, String>> respuesta =
                handler.manejarNoEncontrado(new RecursoNoEncontradoException("Syllabus no encontrado con ID: 99"));

        assertEquals(HttpStatus.NOT_FOUND, respuesta.getStatusCode());
        assertEquals("Syllabus no encontrado con ID: 99", respuesta.getBody().get("error"));
    }

    @Test
    void solicitudInvalidaDa400ConElMensaje() {
        ResponseEntity<Map<String, String>> respuesta =
                handler.manejarSolicitudInvalida(new SolicitudInvalidaException("El resultado no pertenece a la asignatura"));

        assertEquals(HttpStatus.BAD_REQUEST, respuesta.getStatusCode());
        assertEquals("El resultado no pertenece a la asignatura", respuesta.getBody().get("error"));
    }

    @Test
    void violacionDeLlaveForaneaDa409ConMensajeClaro() {
        var causaRaiz = new RuntimeException(
                "Cannot delete or update a parent row: a foreign key constraint fails (`syllabus_resultado_aprendizaje`)");
        ResponseEntity<Map<String, String>> respuesta =
                handler.manejarViolacionDeIntegridad(new DataIntegrityViolationException("fallo", causaRaiz));

        assertEquals(HttpStatus.CONFLICT, respuesta.getStatusCode());
        assertTrue(respuesta.getBody().get("error").contains("está siendo usado por otro"));
    }

    @Test
    void codigoDuplicadoDa409ConMensajeClaro() {
        var causaRaiz = new RuntimeException("Duplicate entry 'SIM-101' for key 'asignatura.codigo_asignatura'");
        ResponseEntity<Map<String, String>> respuesta =
                handler.manejarViolacionDeIntegridad(new DataIntegrityViolationException("fallo", causaRaiz));

        assertEquals(HttpStatus.CONFLICT, respuesta.getStatusCode());
        assertTrue(respuesta.getBody().get("error").contains("ese mismo código"));
    }

    @Test
    void errorInesperadoDa500SinExponerElDetalleInterno() {
        ResponseEntity<Map<String, String>> respuesta =
                handler.manejarErrorInesperado(new NullPointerException("detalle interno sensible"));

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, respuesta.getStatusCode());
        assertEquals("Ocurrió un error inesperado en el servidor.", respuesta.getBody().get("error"));
    }
}
