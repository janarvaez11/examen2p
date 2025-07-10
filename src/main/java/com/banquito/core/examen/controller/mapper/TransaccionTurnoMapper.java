package com.banquito.core.examen.controller.mapper;

import com.banquito.core.examen.model.TransaccionTurno;
import com.banquito.core.examen.controller.dto.ProcesarTransaccionRequest;
import com.banquito.core.examen.controller.dto.TransaccionTurnoResponse;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TransaccionTurnoMapper {
    TransaccionTurno toEntity(ProcesarTransaccionRequest dto);

    TransaccionTurnoResponse toDto(TransaccionTurno entidad);
}