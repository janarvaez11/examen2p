package com.banquito.core.examen.controller.dto;


import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Respuesta al crear o cerrar un turno de caja.
 */
@Data
public class TurnoCajaResponse {
    private String id;
    private String codigoCaja;
    private String codigoCajero;
    private String codigoTurno;                 // p.ej. CAJ01-USU01-20250709
    private BigDecimal montoInicial;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaCierre;
    private BigDecimal montoFinal;
    private String estado;                     // Abierto, Cerrado
    private List<DenominacionDTO> denominacionesIniciales;
    private List<DenominacionDTO> denominacionesFinales;
    private LocalDateTime creationDate;
    private LocalDateTime lastModifiedDate;
    private Long version;
}
