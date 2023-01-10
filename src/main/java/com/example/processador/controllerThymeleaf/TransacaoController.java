package com.example.processador.controllerThymeleaf;

import com.example.processador.model.cliente.Cliente;
import com.example.processador.model.cliente.ClienteRepository;
import com.example.processador.model.cliente.services.ClienteServiceCreate;
import com.example.processador.model.conta.Conta;
import com.example.processador.model.conta.ContaRepository;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.processador.model.transacao.Dto.TypeTransacao.Externo;

@Controller
public class TransacaoController {
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
