package com.example.processador.model.cliente.services;

import com.example.processador.exception.EntidadeNaoProcessavelException;
import com.example.processador.model.cliente.Cliente;
import com.example.processador.model.cliente.ClienteRepository;
import com.example.processador.model.cliente.dto.ClienteCriacaoDto;
import com.example.processador.model.cliente.dto.ClienteRespostaDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.transaction.Transactional;
import javax.validation.Valid;

@Validated
@Service
public class ClienteServiceCreate {
    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ClienteMapper clienteMapper;

    @Transactional
    public ClienteRespostaDto criarCliente(@Valid ClienteCriacaoDto clienteCriacaoDto){
        if (!clienteCriacaoDto.getSenha().equals(clienteCriacaoDto.getValidarSenha())){
            throw new EntidadeNaoProcessavelException(clienteCriacaoDto);
        }
        Cliente clienteDomain = clienteMapper.toDomain(clienteCriacaoDto);
        return clienteMapper.toDto(clienteRepository.saveAndFlush(clienteDomain));
    }
}
