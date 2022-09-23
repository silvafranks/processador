package com.example.processador.model.cliente.services;

import com.example.processador.model.cliente.Cliente;
import com.example.processador.model.cliente.dto.ClienteCriacaoDto;
import com.example.processador.model.cliente.dto.ClienteDto;
import com.example.processador.model.cliente.dto.ClienteRespostaDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClienteMapper {
    Cliente toDomain(ClienteCriacaoDto dto);
    Cliente DtotoDomain(ClienteDto dto);
    ClienteRespostaDto toDto(Cliente domain);
}
