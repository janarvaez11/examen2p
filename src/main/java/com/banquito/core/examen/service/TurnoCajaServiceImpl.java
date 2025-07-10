package com.banquito.core.examen.service;

import com.banquito.core.examen.controller.dto.IniciarTurnoRequest;
import com.banquito.core.examen.controller.dto.ProcesarTransaccionRequest;
import com.banquito.core.examen.controller.dto.CerrarTurnoRequest;

import com.banquito.core.examen.model.TurnoCaja;
import com.banquito.core.examen.model.TransaccionTurno;
import com.banquito.core.examen.model.Denominacion;

import com.banquito.core.examen.exception.TurnoNotFoundException;
import com.banquito.core.examen.exception.EstadoTurnoException;
import com.banquito.core.examen.exception.AlertaDiferenciaException;

import com.banquito.core.examen.repository.TurnoCajaRepository;
import com.banquito.core.examen.repository.TransaccionTurnoRepository;

import com.banquito.core.examen.controller.mapper.TurnoCajaMapper;
import com.banquito.core.examen.controller.mapper.TransaccionTurnoMapper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Slf4j
public class TurnoCajaServiceImpl implements TurnoCajaService {

  private final TurnoCajaRepository turnoRepo;
  private final TransaccionTurnoRepository txRepo;
  private final TurnoCajaMapper turnoMapper;
  private final TransaccionTurnoMapper txMapper;

  public TurnoCajaServiceImpl(
      TurnoCajaRepository turnoRepo,
      TransaccionTurnoRepository txRepo,
      TurnoCajaMapper turnoMapper,
      TransaccionTurnoMapper txMapper) {
    this.turnoRepo = turnoRepo;
    this.txRepo = txRepo;
    this.turnoMapper = turnoMapper;
    this.txMapper = txMapper;
  }

  @Override
  @Transactional
  public TurnoCaja iniciarTurno(IniciarTurnoRequest req) {
    // Mapea DTO → entidad
    TurnoCaja t = turnoMapper.toEntity(req);

    // Genera y asigna código de turno
    t.setCodigoTurno(formarCodigo(req.getCodigoCaja(), req.getCodigoCajero()));

    // Inicializa campos
    t.setFechaInicio(LocalDateTime.now());
    t.setEstado("Abierto");
    t.setCreationDate(LocalDateTime.now());
    t.setLastModifiedDate(LocalDateTime.now());
    t.setVersion(1L);

    return turnoRepo.save(t);
  }

  @Override
  public TransaccionTurno procesarTransaccion(ProcesarTransaccionRequest req) {
    // Verifica turno existe y esté abierto
    TurnoCaja t = turnoRepo.findByCodigoTurno(req.getCodigoTurno())
        .orElseThrow(() -> new TurnoNotFoundException(req.getCodigoTurno()));
    if (!"Abierto".equals(t.getEstado())) {
      throw new EstadoTurnoException("No se puede procesar transacción en turno: " + t.getEstado());
    }

    // Mapea DTO → entidad
    TransaccionTurno tx = txMapper.toEntity(req);
    tx.setFechaTransaccion(LocalDateTime.now());

    return txRepo.save(tx);
  }

  @Override
  @Transactional
  public TurnoCaja cerrarTurno(CerrarTurnoRequest req) {
    // Verifica turno existe y esté abierto
    TurnoCaja t = turnoRepo.findByCodigoTurno(req.getCodigoTurno())
        .orElseThrow(() -> new TurnoNotFoundException(req.getCodigoTurno()));
    if (!"Abierto".equals(t.getEstado())) {
      throw new EstadoTurnoException("El turno ya está " + t.getEstado());
    }

    // Asigna denominaciones finales y fecha de cierre
    t.setDenominacionesFinales(req.getDenominacionesFinales());
    t.setFechaCierre(LocalDateTime.now());
    t.setMontoFinal(calcularTotal(req.getDenominacionesFinales()));
    t.setEstado("Cerrado");

    // Suma de todas las transacciones más el inicial
    BigDecimal esperado = txRepo.findByCodigoTurno(req.getCodigoTurno())
        .stream()
        .map(denomList -> calcularTotal(((TransaccionTurno) denomList).getDenominaciones()))
        .reduce(t.getMontoInicial(), BigDecimal::add);

    // Compara esperados vs reales
    if (t.getMontoFinal().compareTo(esperado) != 0) {
      BigDecimal diff = t.getMontoFinal().subtract(esperado);
      throw new AlertaDiferenciaException("Diferencia en cierre: " + diff);
    }

    // Auditoría
    t.setLastModifiedDate(LocalDateTime.now());
    t.setVersion(t.getVersion() + 1);

    return turnoRepo.save(t);
  }

  // ─── Métodos auxiliares ──────────────────────────────────────────────
  private String formarCodigo(String codigoCaja, String codigoCajero) {
    String fecha = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    return codigoCaja + "-" + codigoCajero + "-" + fecha;
  }

  private BigDecimal calcularTotal(List<Denominacion> denoms) {
    return denoms.stream()
        .map(d -> BigDecimal.valueOf(d.getValor())
            .multiply(BigDecimal.valueOf(d.getCantidad())))
        .reduce(BigDecimal.ZERO, BigDecimal::add);
  }
}
