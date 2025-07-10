# examen2p
Repositorio examen segundo parcial

# Endpoints:


# IniciarTurnoRequest
Uri:/api/v1/turnos/iniciar

{
  "codigoCaja": "CAJ01",
  "codigoCajero": "USU01",
  "montoInicial": 1000.00,
  "denominacionesIniciales": [
    { "valor": 100, "cantidad": 5, "monto": 500.00 },
    { "valor": 20,  "cantidad": 10, "monto": 200.00 }
  ]
}


# ProcesarTransaccionRequest
Uri:/api/v1/turnos/{codigoTurno}/transacciones
{
  "codigoTurno": "CAJ01-USU01-20250709",
  "tipoTransaccion": "Deposito",
  "denominaciones": [
    { "valor": 50, "cantidad": 2, "monto": 100.00 }
  ]
}


# CerrarTurnoRequest
Uri:/api/v1/turnos/{codigoTurno}/cerrar
{
  "codigoTurno": "CAJ01-USU01-20250709",
  "denominacionesFinales": [
    { "valor": 100, "cantidad": 4, "monto": 400.00 },
    { "valor": 20,  "cantidad": 8, "monto": 160.00 }
  ]
}
