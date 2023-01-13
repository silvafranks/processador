package com.example.processador.model.transacao.service;

import com.example.processador.exception.EntidadeNaoEncontradaException;
import com.example.processador.model.cliente.Cliente;
import com.example.processador.model.cliente.ClienteRepository;
import com.example.processador.model.cliente.services.ClienteMapper;
import com.example.processador.model.conta.Conta;
import com.example.processador.model.conta.ContaRepository;
import com.example.processador.model.conta.dto.ContaDto;
import com.example.processador.model.conta.services.ContaMapper;
import com.example.processador.model.transacao.Dto.TransacaoCriacaoDto;
import com.example.processador.model.transacao.Dto.TypeTransacao;
import com.example.processador.model.transacao.Transacao;
import com.example.processador.model.transacao.TransacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.OffsetDateTime;
import java.util.List;

@Validated
@Service
public class TransacaoServiceCreate {

    @Autowired
    private TransacaoRepository transacaoRepository;

    @Autowired
    private ContaMapper contaMapper;

    @Autowired
    private ClienteMapper clienteMapper;

    public void criarTransacao(TransacaoCriacaoDto novoValor, Conta contaEntrada, Conta contaSaida ){


        Transacao novaTransacao = new Transacao();

        novaTransacao.setDataTransacao(OffsetDateTime.now());
        novaTransacao.setValorTransferencia(novoValor.getValorTransferencia());
        novaTransacao.setContaEntrada(contaEntrada);
        novaTransacao.setContaSaida(contaSaida);
        novaTransacao.setType(novoValor.getTypeTransacao());
        transacaoRepository.saveAndFlush(novaTransacao);

    }

}
