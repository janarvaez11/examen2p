package com.banquito.core.examen.controller.dto;

import lombok.Data;
import java.util.List;

import com.banquito.core.examen.model.Denominacion;

@Data

public class ProcesarTransaccionRequest {
    private String codigoTurno;
    private String tipoTransaccion;
    private List<Denominacion> denominaciones;
}