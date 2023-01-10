package com.example.processador.controllerThymeleaf;


import com.example.processador.model.cliente.ClienteRepository;
import com.example.processador.model.cliente.dto.ClienteCriacaoDto;
import com.example.processador.model.cliente.services.ClienteServiceCreate;
import com.example.processador.model.conta.ContaRepository;
import com.example.processador.model.conta.services.ContaServiceCreate;
import com.example.processador.model.conta.services.ContaServiceMutation;
import com.example.processador.model.conta.services.ContaServiceQuery;
import com.example.processador.model.transacao.TransacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
public class ClienteController {
    @Autowired
    private ClienteServiceCreate clienteServiceCreate;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ContaServiceCreate contaServiceCreate;

    @Autowired
    private ContaServiceQuery contaServiceQuery;

    @Autowired
    private ContaServiceMutation contaServiceMutation;

    @Autowired
    private ContaRepository contaRepository;
    @Autowired
    private TransacaoRepository transacaoRepository;

    @RequestMapping(value = "/home")
    public String home(HttpSession httpSession, Model model) {
        model.addAttribute("cliente", httpSession.getAttribute("cliente"));
        return "initial";
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
}
