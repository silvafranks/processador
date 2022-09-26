package com.example.processador.Utils;

import com.example.processador.exception.EntidadeNaoEncontradaException;
import com.example.processador.exception.EntidadeNaoProcessavelException;
import com.example.processador.model.cliente.Cliente;
import com.example.processador.model.cliente.ClienteRepository;
import com.example.processador.model.conta.Conta;
import com.example.processador.model.conta.dto.ContaCriacaoDto;
import com.example.processador.model.patrimonio.Patrimonio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContaUtils {

    ;



    public Conta filtrarContaPorCliente(Patrimonio patrimonio, Integer idConta) {

        return patrimonio.getContas().stream().filter(conta -> conta.getIdConta() == idConta)
                .findAny().orElseThrow(() -> new EntidadeNaoEncontradaException("conta", Conta.class));
    }

    public void VerificarBanco(Cliente cliente,ContaCriacaoDto contaCriacaoDto ){

        List<Conta> contas = cliente.getPatrimonio().getContas();
        System.out.println("CONTAS  "+ contas);
        if (!contas.isEmpty()){
            for (Conta c : contas) {
                if (c.getBanco().equals(contaCriacaoDto.getBanco())) {
                    throw new EntidadeNaoProcessavelException(cliente);
                }
            }
        }
    }
}
