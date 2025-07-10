package com.banquito.core.examen.service;

import com.banquito.core.examen.model.TransaccionTurno;

import com.banquito.core.examen.model.TurnoCaja;

import com.banquito.core.examen.controller.dto.IniciarTurnoRequest;

import com.banquito.core.examen.controller.dto.ProcesarTransaccionRequest;

import com.banquito.core.examen.controller.dto.CerrarTurnoRequest;

public interface TurnoCajaService {
    TurnoCaja iniciarTurno(IniciarTurnoRequest req);

    TransaccionTurno procesarTransaccion(ProcesarTransaccionRequest req);

    TurnoCaja cerrarTurno(CerrarTurnoRequest req);
}