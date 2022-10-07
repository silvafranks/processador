package com.example.processador.model.transacao.service;

import com.example.processador.model.transacao.Transacao;
import com.example.processador.model.transacao.TransacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

@Service
public class TransacaoServiceQuery {

    @Autowired
    private TransacaoRepository transacaoRepository;


    public List<Transacao> getTransacoes(Integer idcliente){
     return transacaoRepository.findAllTransacoesConta(idcliente);
    }

}
