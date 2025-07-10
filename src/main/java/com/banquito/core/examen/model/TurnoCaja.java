package com.banquito.core.examen.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


@Data
@Document(collection = "turno_cajas")
public class TurnoCaja {
    @Id
    private String id;
    private String codigoCaja;
    private String codigoCajero;
    private String codigoTurno;            // e.g. CAJ01-USU01-20250709
    private BigDecimal montoInicial;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaCierre;
    private BigDecimal montoFinal;
    private String estado;                // Abierto, Cerrado
    private List<Denominacion> denominacionesIniciales;
    private List<Denominacion> denominacionesFinales;

    // Auditoría
    private LocalDateTime creationDate;
    private LocalDateTime lastModifiedDate;
    private Long version;
}
