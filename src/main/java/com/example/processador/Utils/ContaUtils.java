package com.example.processador.Utils;

import com.example.processador.exception.EntidadeConflitoException;
import com.example.processador.exception.EntidadeNaoEncontradaException;
import com.example.processador.model.cliente.Cliente;
import com.example.processador.model.cliente.dto.ClienteDto;
import com.example.processador.model.cliente.services.ClienteMapper;
import com.example.processador.model.conta.Conta;
import com.example.processador.model.conta.ContaRepository;
import com.example.processador.model.conta.dto.ContaCriacaoDto;
import com.example.processador.model.conta.services.ContaMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@Validated
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ContaUtils {
    private final ContaMapper contaMapper;

    private final ContaRepository contaRepository;
    private final ClienteMapper clienteMapper;

    public Conta filtrarContaPorCliente(Cliente cliente, Integer idConta) {

        ClienteDto clienteDto = clienteMapper.DomaintoClienteDto(cliente);
        List<Conta> contas = contaRepository.findByCliente(cliente);
        return contas.stream().filter(conta -> conta.getIdConta() == idConta)
                .findAny().orElseThrow(() -> new EntidadeNaoEncontradaException("conta", Conta.class));
    }

    public void VerificarBanco(Cliente cliente, ContaCriacaoDto contaCriacaoDto) {


        List<Conta> contas = contaRepository.findByCliente(cliente);

        contas.forEach(i->{
            if (i.getBanco().equals(contaCriacaoDto.getBanco())){
                throw new EntidadeConflitoException(contaCriacaoDto, "Cliente já contém esse banco");
            }
        });
    }
}
