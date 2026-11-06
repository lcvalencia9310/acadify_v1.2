package com.example.acadify.exception;

/** Se lanza cuando los datos de la petición son válidos en forma pero no en contenido (ej. una relación que no corresponde). El GlobalExceptionHandler la mapea a 400. */
public class SolicitudInvalidaException extends RuntimeException {
    public SolicitudInvalidaException(String mensaje) {
        super(mensaje);
    }
}
