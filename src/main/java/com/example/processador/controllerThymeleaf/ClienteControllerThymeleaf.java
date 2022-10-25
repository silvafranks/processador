package com.example.processador.controllerThymeleaf;

import com.example.processador.model.cliente.dto.ClienteCriacaoDto;
import com.example.processador.model.cliente.services.ClienteServiceCreate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class ClienteControllerThymeleaf {
    @Autowired
    private ClienteServiceCreate clienteServiceCreate;


    @RequestMapping(value = "/jsp")
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/cadastro")
    public String cadastrar() {


        return "/cadastro_cliente";
    }

    @PostMapping(value = "/cadastrar")
    public String cadastrando(ClienteCriacaoDto clienteCriacaoDto) {

        clienteServiceCreate.criarCliente(clienteCriacaoDto);
        return "login";
    }
}
