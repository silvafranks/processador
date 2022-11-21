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
import java.time.OffsetDateTime;
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
    public String home(HttpSession httpSession, ClienteLoginDto clienteLoginDto, Model model) {
        Cliente byEmail = clienteRepository.findByEmail(clienteLoginDto.getEmail());
        if (byEmail==null){
            model.addAttribute("cliente",httpSession.getAttribute("cliente"));
            return "initial";
        }
        Optional<Cliente> cliente = clienteRepository.findById(byEmail.getId());

        httpSession.setAttribute("patrimonio",cliente.get().getTotalPatrimonio());
        httpSession.setAttribute("cliente",byEmail);
        httpSession.setAttribute("idcliente", clienteLoginDto.getId());

        model.addAttribute("cliente",byEmail);
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

        model.addAttribute("cliente", byId.get());
        return "initial";
    }
    @RequestMapping(value = "/conta/lista/{idCliente}")
    public String buscarContasPorClientes( @PathVariable Integer idCliente, Model model){
        List<Conta> contas = contaServiceQuery.contas(idCliente);

        model.addAttribute("contas",contas);
        return "contas";
    }

    @RequestMapping(value = "/transferir/{idCliente}")
    public String fazerTransferencia( @PathVariable Integer idCliente, Model model){

        List<Conta> contas = contaServiceQuery.contas(idCliente);
        model.addAttribute("idCliente",idCliente);
        model.addAttribute("contas",contas);

        return "transferencias";
    }
    @RequestMapping(value = "/transferir")
    public String transferir(TransacaoInfoDto transacaoInfoDto, Model model, HttpSession httpSession){
        TransacaoCriacaoDto transacaoCriacaoDto = new TransacaoCriacaoDto();
        transacaoCriacaoDto.setDataTransacao(OffsetDateTime.now());
        transacaoCriacaoDto.setTypeTransacao(Externo);
        transacaoCriacaoDto.setValorTransferencia(transacaoInfoDto.getValorTransferencia());

        List<Conta> contaSaida = contaRepository.findByCliente_IdAndBanco(transacaoInfoDto.getIdCliente(), transacaoInfoDto.getContaSaida());
        List<Conta> contaEntrada = contaRepository.findByCliente_IdAndBanco(transacaoInfoDto.getIdCliente(), transacaoInfoDto.getContaEntrada() );


        contaServiceMutation.transferenciaInternaDeContas(transacaoInfoDto.getIdCliente(),contaSaida.get(0).getIdConta(),contaEntrada.get(0).getIdConta(),transacaoCriacaoDto);
        model.addAttribute("cliente",httpSession.getAttribute("cliente"));
        return "initial";
    }

    @RequestMapping(value = "/desvincular_conta/{idCliente}")
    public String desvicularConta(@PathVariable Integer idCliente, Model model){
            Context context = new Context();

        Optional<Cliente> cliente = clienteRepository.findById(idCliente);
        List<Conta> contaByCliente = contaRepository.findByCliente(cliente.get());


        model.addAttribute("contas",contaByCliente);
        return "desvincular_conta";
    }

    @RequestMapping(value = "/desvincular")
    @Transactional
    public String desvicular(DeletarConta deletarConta, Model model,HttpSession httpSession){
        Optional<Conta> conta = contaRepository.findById(deletarConta.getIdContaASerDeletada());

        conta.get().setFlag(EnumFlag.Desativado);

        model.addAttribute("cliente",httpSession.getAttribute("cliente"));
        return "initial";
    }

//    @GetMapping("/grafico")
//    public String getGrafico(Model model, Integer idCliente){
//        Map<String, Integer> graphData = new TreeMap<>();
//        List<Conta> contas = contaServiceQuery.contas(1);
//
//        for (Conta conta:contas) {
//            graphData.put(conta.getBanco().toString(),conta.getValorDisponivel().intValue());
//        }
////        graphData.put("2016", 147);
////        graphData.put("2017", 1256);
////        graphData.put("2018", 3856);
////        graphData.put("2019", 19807);
//        model.addAttribute("chartData", graphData);
//        return "chart";
//    }
}
