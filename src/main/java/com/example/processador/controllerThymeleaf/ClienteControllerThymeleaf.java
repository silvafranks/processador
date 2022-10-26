package com.example.processador.controllerThymeleaf;

import com.example.processador.model.cliente.Cliente;
import com.example.processador.model.cliente.ClienteRepository;
import com.example.processador.model.cliente.dto.ClienteCriacaoDto;
import com.example.processador.model.cliente.dto.ClienteLoginDto;
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

    @Autowired
    private ClienteRepository clienteRepository;

    @RequestMapping(value = "/login")
    public String index() {
        return "login";
    }

    @RequestMapping(value = "/cadastro")
    public String cadastrar() {


        return "/cadastro_cliente";
    }

    @PostMapping(value = "/logar")
    public String cadastrando(ClienteCriacaoDto clienteCriacaoDto) {

        clienteServiceCreate.criarCliente(clienteCriacaoDto);
        return "login";
    }
    @RequestMapping(value = "/efetuarLogin")
    public String efetuarLogin(ClienteLoginDto clienteLoginDto ) {
        Cliente byEmail = clienteRepository.findByEmail(clienteLoginDto.getEmail());
        if (byEmail == null){
            throw new RuntimeException("Usuario não existe.");
        }
        if (!clienteLoginDto.getSenha().equals(byEmail.getSenha())){
            throw new RuntimeException("Credenciais Erradas");
        }
        return "initial";
    }

}
