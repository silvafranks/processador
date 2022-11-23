package com.example.processador.model.conta.services;

import com.example.processador.exception.EntidadeNaoEncontradaException;
import com.example.processador.model.cliente.Cliente;
import com.example.processador.model.cliente.ClienteRepository;
import com.example.processador.model.cliente.services.ClienteMapper;
import com.example.processador.model.conta.Conta;
import com.example.processador.model.conta.ContaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Service
@Validated
public class ContaServiceQuery {

    @Autowired
    private ContaRepository contaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ContaMapper contaMapper;

    @Autowired
    private ClienteMapper clienteMapper;

   public List<Conta> contas(Integer idCliente){
       Optional<Cliente> cliente = clienteRepository.findById(idCliente);

       if (cliente.isEmpty()){
           throw new EntidadeNaoEncontradaException("ID não encontrado","Cliente");
       }
       List<Conta> contaDto = contaRepository.findByCliente(cliente.get());

       return contaDto;
   }
}
