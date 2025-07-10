package com.banquito.core.examen.model;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Document(collection = "transaccion_turnos")
public class TransaccionTurno {
    @Id
    private String id;
    private String codigoCaja;
    private String codigoCajero;
    private String codigoTurno;
    private String tipoTransaccion;       // Inicio, Retiro, Deposito, Cierre
    private LocalDateTime fechaTransaccion;
    private List<Denominacion> denominaciones;
}
