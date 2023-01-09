package com.example.processador.controllerThymeleaf;

import com.example.processador.model.cliente.Cliente;
import com.example.processador.model.cliente.ClienteLoginDto;
import com.example.processador.model.cliente.ClienteRepository;
import com.example.processador.model.cliente.dto.ClienteCriacaoDto;
import com.example.processador.model.cliente.services.ClienteServiceCreate;
import com.example.processador.model.conta.Conta;
import com.example.processador.model.conta.ContaRepository;
import com.example.processador.model.conta.dto.ContaCriacaoDto;
import com.example.processador.model.conta.dto.DeletarConta;
import com.example.processador.model.conta.dto.EnumFlag;
import com.example.processador.model.conta.services.ContaServiceCreate;
import com.example.processador.model.conta.services.ContaServiceMutation;
import com.example.processador.model.conta.services.ContaServiceQuery;
import com.example.processador.model.transacao.Dto.TransacaoCriacaoDto;
import com.example.processador.model.transacao.Dto.TransacaoInfoDto;
import com.example.processador.model.transacao.Transacao;
import com.example.processador.model.transacao.TransacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.context.Context;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.math.BigInteger;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.processador.model.transacao.Dto.TypeTransacao.Externo;

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

    @Autowired
    private ContaServiceMutation contaServiceMutation;

    @Autowired
    private ContaRepository contaRepository;
    @Autowired
    private TransacaoRepository transacaoRepository;

//    @RequestMapping(value = "/login")
//    public String index() {
//
//        return "login";
//    }

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
    public String home(HttpSession httpSession, ClienteLoginDto clienteLoginDto, Model model) {
        System.out.println(httpSession.getAttribute("cliente"));

        Cliente byEmail = clienteRepository.findByEmail(clienteLoginDto.getUsername()).get();
        if (byEmail == null) {
            model.addAttribute("cliente", httpSession.getAttribute("cliente"));
                return "initial";
        }
        Optional<Cliente> cliente = clienteRepository.findById(byEmail.getId());

        httpSession.setAttribute("patrimonio", cliente.get().getTotalPatrimonio());
        httpSession.setAttribute("cliente", byEmail);
        httpSession.setAttribute("idcliente", clienteLoginDto.getId());

        model.addAttribute("cliente", byEmail);
        return "initial";
    }

    @RequestMapping(value = "/cadastrar_conta/{idCliente}")
    public String cadastrarConta(ContaCriacaoDto contaCriacaoDto, @PathVariable Integer idCliente, Model model) {

        Optional<Cliente> byId = clienteRepository.findById(idCliente);
        contaCriacaoDto.setUltimaMovimentacao(OffsetDateTime.now());
        contaServiceCreate.criarConta(idCliente, contaCriacaoDto);

        ClienteLoginDto clienteLoginDto = new ClienteLoginDto();
        clienteLoginDto.setUsername(byId.get().getEmail());
        clienteLoginDto.setPassword(byId.get().getSenha());

        model.addAttribute("cliente", byId.get());
        return "initial";
    }

    @RequestMapping(value = "/conta/lista/{idCliente}")
    public String buscarContasPorClientes(@PathVariable Integer idCliente, Model model) {
        List<Conta> contas = contaServiceQuery.contas(idCliente);

        model.addAttribute("contas", contas);
        return "contas";
    }

    @RequestMapping(value = "/transferir/{idCliente}")
    public String fazerTransferencia(@PathVariable Integer idCliente, Model model) {

        List<Conta> contas = contaServiceQuery.contas(idCliente);
        Optional<Cliente> cliente = clienteRepository.findById(idCliente);
        System.out.println("CLIENTE" + cliente);
        model.addAttribute("cliente", cliente.get());
        model.addAttribute("idCliente", idCliente);
        model.addAttribute("contas", contas);

        return "transferencias";
    }

    @RequestMapping(value = "/transferir")
    public String transferir(TransacaoInfoDto transacaoInfoDto, Model model, HttpSession httpSession) {
        TransacaoCriacaoDto transacaoCriacaoDto = new TransacaoCriacaoDto();
        transacaoCriacaoDto.setDataTransacao(OffsetDateTime.now());
        transacaoCriacaoDto.setTypeTransacao(Externo);
        transacaoCriacaoDto.setValorTransferencia(transacaoInfoDto.getValorTransferencia());

        List<Conta> contaSaida = contaRepository.findByCliente_IdAndBanco(transacaoInfoDto.getIdCliente(), transacaoInfoDto.getContaSaida());
        List<Conta> contaEntrada = contaRepository.findByCliente_IdAndBanco(transacaoInfoDto.getIdCliente(), transacaoInfoDto.getContaEntrada());


        contaServiceMutation.transferenciaInternaDeContas(transacaoInfoDto.getIdCliente(), contaSaida.get(0).getIdConta(), contaEntrada.get(0).getIdConta(), transacaoCriacaoDto);
        model.addAttribute("cliente", httpSession.getAttribute("cliente"));
        return "initial";
    }

    @RequestMapping(value = "/desvincular_conta/{idCliente}")
    public String desvicularConta(@PathVariable Integer idCliente, Model model) {

        Optional<Cliente> cliente = clienteRepository.findById(idCliente);
        List<Conta> contaByCliente = contaRepository.findByCliente(cliente.get());


        model.addAttribute("contas", contaByCliente);
        return "desvincular_conta";
    }

    @RequestMapping(value = "/desvincular")
    @Transactional
    public String desvicular(DeletarConta deletarConta, Model model, HttpSession httpSession) {
        Optional<Conta> conta = contaRepository.findById(deletarConta.getIdContaASerDeletada());

        conta.get().setFlag(EnumFlag.Desativado);

        model.addAttribute("cliente", httpSession.getAttribute("cliente"));
        return "initial";
    }

    @RequestMapping(value = "/deletar/{idCliente}")
    public String deletar(Model model, HttpSession httpSession, @PathVariable Integer idCliente) {
        List<Conta> contas = contaRepository.findByCliente_Id(idCliente);
        for (Conta conta : contas) {
            transacaoRepository.deleteByContaEntradaAndContaSaida(conta);
        }

        model.addAttribute("cliente", httpSession.getAttribute("cliente"));
        return "initial";
    }

    @RequestMapping(value = "/transacoes/{idCliente}")
    public String visualizarTransacoes(Model model, HttpSession httpSession, @PathVariable Integer idCliente) {
        List<Conta> contas = contaRepository.findByCliente_Id(idCliente);

        List<Transacao> transacaosEntrada = new ArrayList<>();


        for (Conta conta : contas) {
            transacaosEntrada.addAll(transacaoRepository.findByContaEntrada(conta));
        }

        System.out.println(transacaosEntrada);
        model.addAttribute("Transacoes", transacaosEntrada);
        model.addAttribute("cliente", httpSession.getAttribute("cliente"));
        return "historico";
    }
}
