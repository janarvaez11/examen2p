
package com.banquito.core.examen.service;


import com.banquito.core.examen.controller.dto.ProcesarTransaccionRequest;
import com.banquito.core.examen.controller.dto.IniciarTurnoRequest;
import com.banquito.core.repository.TurnoCajaServiceImpl;
import com.banquito.core.cuentas.excepciones.OperacionNoPermitidaException;
import com.banquito.core.cuentas.mapper.TipoCuentaMapper;
import com.banquito.core.examen.model.TurnoCaja;
import com.banquito.core.examen.model.TransaccionTurno;

import java.time.LocalDateTime;
import java.util.List;
import com.banquito.core.cuentas.repositorio.TipoCuentaRepositorio;
import com.banquito.core.cuentas.repositorio.TasaInteresRepositorio;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;






@Service
public class TurnoCajaServiceImpl implements TurnoCajaService {
  private final TurnoCajaRepository turnoRepo;
  private final TransaccionTurnoRepository txRepo;

  public TurnoCajaServiceImpl(TurnoCajaRepository t, TransaccionTurnoRepository x) {
    this.turnoRepo = t;
    this.txRepo = x;
  }

  @Transactional
  public TurnoCaja iniciarTurno(IniciarTurnoRequest req) {
    TurnoCaja t = new TurnoCajaMapperImpl().toEntity(req);
    t.setCodigoTurno(formarCodigo(req.getCodigoCaja(), req.getCodigoCajero()));
    t.setFechaInicio(LocalDateTime.now());
    t.setEstado("Abierto");
    t.setCreationDate(LocalDateTime.now());
    t.setLastModifiedDate(LocalDateTime.now());
    t.setVersion(1L);
    return turnoRepo.save(t);
  }

  public TransaccionTurno procesarTransaccion(ProcesarTransaccionRequest req) {
    TurnoCaja t = turnoRepo.findByCodigoTurno(req.getCodigoTurno())
        .orElseThrow(() -> new TurnoNotFoundException(req.getCodigoTurno()));
    if (!"Abierto".equals(t.getEstado())) {
      throw new EstadoTurnoException("No se puede transaccionar turno " + t.getEstado());
    }
    TransaccionTurno tx = new TransaccionTurnoMapperImpl().toEntity(req);
    tx.setFechaTransaccion(LocalDateTime.now());
    return txRepo.save(tx);
  }

  @Transactional
  public TurnoCaja cerrarTurno(CerrarTurnoRequest req) {
    TurnoCaja t = turnoRepo.findByCodigoTurno(req.getCodigoTurno())
        .orElseThrow(() -> new TurnoNotFoundException(req.getCodigoTurno()));
    if (!"Abierto".equals(t.getEstado())) {
      throw new EstadoTurnoException("Turno ya cerrado");
    }
    t.setDenominacionesFinales(req.getDenominacionesFinales());
    t.setFechaCierre(LocalDateTime.now());
    t.setMontoFinal(calcularTotal(req.getDenominacionesFinales()));
    t.setEstado("Cerrado");
    // validar suma de transacciones vs montoFinal
    BigDecimal esperado = txRepo.findByCodigoTurno(req.getCodigoTurno()).stream()
        .map(tx -> calcularTotal(tx.getDenominaciones()))
        .reduce(BigDecimal.ZERO, BigDecimal::add)
        .add(t.getMontoInicial());
    if (t.getMontoFinal().compareTo(esperado) != 0) {
      throw new AlertaDiferenciaException("Diferencia de " + t.getMontoFinal().subtract(esperado));
    }
    t.setLastModifiedDate(LocalDateTime.now());
    t.setVersion(t.getVersion() + 1);
    return turnoRepo.save(t);
  }

  // métodos auxiliares: formarCodigo, calcularTotal(...)
}
