package com.example.processador.model.conta.services;

import com.example.processador.model.conta.Conta;
import com.example.processador.model.conta.dto.ContaCriacaoDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ContaMapper {

    Conta toDomain(ContaCriacaoDto dto);

    ContaCriacaoDto toDto(Conta domain);
}
