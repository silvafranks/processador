package com.example.processador.model.patrimonio.service;

import com.example.processador.model.patrimonio.Patrimonio;
import com.example.processador.model.patrimonio.dto.PatrimonioDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PatrimonioMapper {

    PatrimonioDto toDto(Patrimonio domain);

}
