package com.example.processador.model.conta.services;

import com.example.processador.exception.EntidadeNaoEncontradaException;
import com.example.processador.model.cliente.Cliente;
import com.example.processador.model.cliente.ClienteRepository;
import com.example.processador.model.conta.ContaRepository;
import com.example.processador.model.patrimonio.Patrimonio;
import com.example.processador.model.patrimonio.PatrimonioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;

@Service
@Validated
public class ContaServiceQuery {

    @Autowired
    private ContaRepository contaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private PatrimonioRepository patrimonioRepository;

   public Optional<Patrimonio> contas(Integer idCliente){
       Optional<Cliente> cliente = clienteRepository.findById(idCliente);

       if (cliente.isEmpty()){
           throw new EntidadeNaoEncontradaException("ID não encontrado","Cliente");
       }
       Integer idPatrimonio = cliente.get().getPatrimonio().getId();

       Optional<Patrimonio> patrimonio = patrimonioRepository.findById(idPatrimonio);

       return patrimonio;
   }
}
