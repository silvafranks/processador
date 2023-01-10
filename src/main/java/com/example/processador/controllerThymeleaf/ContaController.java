package com.example.processador.controllerThymeleaf;

import com.example.processador.model.cliente.Cliente;
import com.example.processador.model.cliente.ClienteLoginDto;
import com.example.processador.model.cliente.ClienteRepository;
import com.example.processador.model.cliente.services.ClienteServiceCreate;
import com.example.processador.model.conta.Conta;
import com.example.processador.model.conta.ContaRepository;
import com.example.processador.model.conta.dto.ContaCriacaoDto;
import com.example.processador.model.conta.dto.DeletarConta;
import com.example.processador.model.conta.dto.EnumFlag;
import com.example.processador.model.conta.services.ContaServiceCreate;
import com.example.processador.model.conta.services.ContaServiceMutation;
import com.example.processador.model.conta.services.ContaServiceQuery;
import com.example.processador.model.transacao.TransacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Controller
public class ContaController {
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
}
