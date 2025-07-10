
package com.banquito.core.examen.controller;



@RestController
@RequestMapping("/api/v1/turnos")
@Tag(name = "Turnos Caja", description = "Administración de turnos de ventanilla")
@Slf4j
public class TurnoCajaController {
    private final TurnoCajaService service;
    private final TurnoCajaMapper turnoMapper;
    private final TransaccionTurnoMapper txMapper;

    public TurnoCajaController(TurnoCajaService s, TurnoCajaMapper m, TransaccionTurnoMapper n) {
        this.service = s; this.turnoMapper = m; this.txMapper = n;
    }

    @Operation(summary = "Iniciar turno", description = "Registra el monto inicial y abre un nuevo turno")
    @ApiResponses({
      @ApiResponse(responseCode="200", description="Turno iniciado"),
      @ApiResponse(responseCode="400", description="Error en datos de entrada")
    })
    @PostMapping("/iniciar")
    public TurnoCajaResponse iniciar(@RequestBody IniciarTurnoRequest req) {
        log.info("Iniciando turno: {}", req);
        return turnoMapper.toDto(service.iniciarTurno(req));
    }

    @Operation(summary = "Procesar transacción", description = "Registra billetes entregados o recibidos en un turno abierto")
    @PostMapping("/{codigoTurno}/transacciones")
    public TransaccionTurnoResponse transact(
        @Parameter(description="Código del turno") @PathVariable String codigoTurno,
        @RequestBody ProcesarTransaccionRequest req) {
      log.info("Nuevo movimiento para turno {}: {}", codigoTurno, req);
      req.setCodigoTurno(codigoTurno);
      return txMapper.toDto(service.procesarTransaccion(req));
    }

    @Operation(summary = "Cerrar turno", description = "Registra el dinero final y valida diferencias")
    @PostMapping("/{codigoTurno}/cerrar")
    public TurnoCajaResponse cerrar(
        @PathVariable String codigoTurno,
        @RequestBody CerrarTurnoRequest req) {
      log.info("Cerrando turno {}: {}", codigoTurno, req);
      req.setCodigoTurno(codigoTurno);
      return turnoMapper.toDto(service.cerrarTurno(req));
    }
}

