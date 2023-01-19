package com.example.processador.model.transacao.service;

import com.example.processador.config.authetication.AuthenticationUtils;
import com.example.processador.model.cliente.Cliente;
import com.example.processador.model.cliente.ClienteRepository;
import com.example.processador.model.transacao.Transacao;
import com.example.processador.model.transacao.TransacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Service
public class TransacaoServiceQuery {

    @Autowired
    private TransacaoRepository transacaoRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private AuthenticationUtils authenticationUtils;


    public List<Transacao> getTransacoes(Integer idCliente,String token){
        authenticationUtils.verificaIdCliente(idCliente, token);

        Optional<Cliente> byId = clienteRepository.findById(idCliente);

        return transacaoRepository.findAllTransacoesConta(byId.get().getId());
    }

}
