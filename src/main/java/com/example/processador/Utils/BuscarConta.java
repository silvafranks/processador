package com.example.processador.Utils;

import com.example.processador.exception.EntidadeNaoEncontradaException;
import com.example.processador.model.conta.Conta;
import com.example.processador.model.patrimonio.Patrimonio;

public class BuscarConta {


    public Conta filtrarContaPorCliente(Patrimonio patrimonio, Integer idConta) {

        return patrimonio.getContas().stream().filter(conta -> conta.getIdConta() == idConta)
                .findAny().orElseThrow(() -> new EntidadeNaoEncontradaException("conta", Conta.class));
    }
}
