package com.example.processador.controllerThymeleaf;

import com.example.processador.model.cliente.Cliente;
import com.example.processador.model.cliente.ClienteRepository;
import com.example.processador.model.cliente.dto.ClienteCriacaoDto;
import com.example.processador.model.cliente.dto.ClienteLoginDto;
import com.example.processador.model.cliente.services.ClienteServiceCreate;
import com.example.processador.model.conta.Conta;
import com.example.processador.model.conta.dto.ContaCriacaoDto;
import com.example.processador.model.conta.services.ContaServiceCreate;
import com.example.processador.model.conta.services.ContaServiceQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.http.HttpClient;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Controller
public class ClienteControllerThymeleaf {
    @Autowired
    private ClienteServiceCreate clienteServiceCreate;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ContaServiceCreate contaServiceCreate;

    @Autowired
    private ContaServiceQuery contaServiceQuery;


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

    @RequestMapping(value = "/home")
    public String home(ClienteLoginDto clienteLoginDto, Model model) {
        Cliente byEmail = clienteRepository.findByEmail(clienteLoginDto.getEmail());

//        if (byEmail == null) {
//            throw new RuntimeException("Usuario não existe.");
//        }
//        if (!clienteLoginDto.getSenha().equals(byEmail.getSenha())) {
//            throw new RuntimeException("Credenciais Erradas");
//        }
        System.out.println(byEmail);

        model.addAttribute("Cliente",byEmail);
        return "initial";
    }


    @RequestMapping(value = "/cadastrar_conta/{idCliente}")
    public String cadastrarConta(ContaCriacaoDto contaCriacaoDto, @PathVariable Integer idCliente,Model model) {

        Optional<Cliente> byId = clienteRepository.findById(idCliente);
        contaCriacaoDto.setUltimaMovimentacao(OffsetDateTime.now());
        contaServiceCreate.criarConta(idCliente, contaCriacaoDto);

        ClienteLoginDto clienteLoginDto = new ClienteLoginDto();
        clienteLoginDto.setEmail(byId.get().getEmail());
        clienteLoginDto.setSenha(byId.get().getSenha());

        System.out.println(byId.get().getId());
        model.addAttribute("Cliente", byId.get());
        return "initial";
    }
    @RequestMapping(value = "/conta/lista/{idCliente}")
    public String buscarContasPorClientes(@PathVariable Integer idCliente, Model model){
        List<Conta> contas = contaServiceQuery.contas(idCliente);
        model.addAttribute("contas",contas);
        return "contas";
    }
}
