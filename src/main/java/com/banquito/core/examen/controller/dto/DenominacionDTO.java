package com.banquito.core.examen.controller.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class DenominacionDTO {
    private int valor; // p.ej. 1, 5, 10, 20, 50, 100
    private int cantidad; // número de billetes
    private BigDecimal monto; // valor × cantidad
}
