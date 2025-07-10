package com.banquito.core.examen.controller.mapper;

import com.banquito.core.examen.model.TurnoCaja;
import com.banquito.core.examen.controller.dto.IniciarTurnoRequest;
import com.banquito.core.examen.controller.dto.TurnoCajaResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TurnoCajaMapper {
    TurnoCaja toEntity(IniciarTurnoRequest dto);

    TurnoCajaResponse toDto(TurnoCaja entidad);
}