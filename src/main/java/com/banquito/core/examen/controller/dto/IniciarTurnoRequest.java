package com.banquito.core.examen.controller.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

import com.banquito.core.examen.model.Denominacion;

@Data

public class IniciarTurnoRequest {
    private String codigoCaja;
    private String codigoCajero;
    private BigDecimal montoInicial;
    private List<Denominacion> denominacionesIniciales;
}