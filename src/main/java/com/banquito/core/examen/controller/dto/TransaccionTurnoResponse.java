package com.banquito.core.examen.controller.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class TransaccionTurnoResponse {
    private String id;
    private String codigoCaja;
    private String codigoCajero;
    private String codigoTurno;
    private String tipoTransaccion; // Inicio, Retiro, Depósito, Cierre
    private LocalDateTime fechaTransaccion;
    private List<DenominacionDTO> denominaciones;
}
