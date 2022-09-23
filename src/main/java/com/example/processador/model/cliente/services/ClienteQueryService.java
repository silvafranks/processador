package com.example.processador.model.cliente.services;

import com.example.processador.model.cliente.Cliente;
import com.example.processador.model.cliente.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Validated
@Service
public class ClienteQueryService {

    @Autowired
    private ClienteRepository clienteRepository;


    public List<Cliente> allClientes() {
        List<Cliente> all = clienteRepository.findAll();
        return all;
    }

    public Optional<Cliente> BuscarPorId(Integer id) {

        Optional<Cliente> byId = clienteRepository.findById(id);
        return byId;
    }
}
