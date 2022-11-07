package com.example.processador.model.conta.services;

import com.example.processador.model.conta.Conta;
import com.example.processador.model.conta.dto.ContaCriacaoDto;
import com.example.processador.model.conta.dto.ContaDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ContaMapper {

    Conta toDomain(ContaCriacaoDto dto);

    ContaCriacaoDto toDto(Conta domain);

    ContaDto domainToContaDto(Conta conta);

    Conta contaDtoToDomain(ContaDto contaDto);

}
