package com.banquito.core.examen.controller.dto;

import java.util.List;
import lombok.Data;
import com.banquito.core.examen.model.Denominacion;

@Data

public class CerrarTurnoRequest {
    private String codigoTurno;
    private List<Denominacion> denominacionesFinales;
}