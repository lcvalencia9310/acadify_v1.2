package com.example.acadify.exception;

/** Se lanza cuando se busca/actualiza/elimina un registro que no existe. El GlobalExceptionHandler la mapea a 404. */
public class RecursoNoEncontradoException extends RuntimeException {
    public RecursoNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}
