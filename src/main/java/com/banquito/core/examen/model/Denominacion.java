package com.banquito.core.examen.model;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class Denominacion {
    private int valor;           // 1, 5, 10, 20, 50, 100
    private int cantidad;        // número de billetes
    private BigDecimal monto;    // valor * cantidad
}