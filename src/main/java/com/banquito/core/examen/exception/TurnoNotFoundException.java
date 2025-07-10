package com.banquito.core.examen.exception;

public class TurnoNotFoundException extends RuntimeException {
    public TurnoNotFoundException(String codigoTurno) {
        super("Turno no encontrado: " + codigoTurno);
    }
}
