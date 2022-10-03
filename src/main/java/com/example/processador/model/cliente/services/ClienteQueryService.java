package com.example.processador.model.cliente.services;

import com.example.processador.model.cliente.Cliente;
import com.example.processador.model.cliente.ClienteRepository;
import com.example.processador.model.patrimonio.Patrimonio;
import com.example.processador.model.patrimonio.dto.PatrimonioDto;
import com.example.processador.model.patrimonio.service.PatrimonioMapper;
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

    private PatrimonioMapper patrimonioMapper;

    public List<Cliente> allClientes() {
        List<Cliente> all = clienteRepository.findAll();
        return all;
    }

    public Optional<Cliente> BuscarPorId(Integer id) {

        Optional<Cliente> byId = clienteRepository.findById(id);
        return byId;
    }

//    public Patrimonio BuscarPatrimonios(Integer idCliente) {
//
//        Optional<Cliente> cliente = clienteRepository.findById(idCliente);
//
//        Patrimonio patrimonio = cliente.get().getPatrimonio();
//
//        System.out.println(patrimonio);
//
//        return patrimonio;
//    }
}
