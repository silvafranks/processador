package com.example.processador.model.conta.services;

import com.example.processador.Utils.BuscarConta;
import com.example.processador.exception.EntidadeNaoEncontradaException;
import com.example.processador.exception.EntidadeNaoProcessavelException;
import com.example.processador.model.cliente.Cliente;
import com.example.processador.model.cliente.ClienteRepository;
import com.example.processador.model.conta.Conta;
import com.example.processador.model.conta.ContaRepository;
import com.example.processador.model.conta.dto.ContaMutationDto;
import com.example.processador.model.patrimonio.Patrimonio;
import com.example.processador.model.patrimonio.PatrimonioRepository;
import com.example.processador.model.transacao.Dto.TransacaoCriacaoDto;
import com.example.processador.model.transacao.Transacao;
import com.example.processador.model.transacao.TransacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.transaction.Transactional;
import java.time.OffsetDateTime;
import java.util.Optional;

@Validated
@Service
public class ContaServiceMutation {

    @Autowired
    private ContaRepository contaRepository;
    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private PatrimonioRepository patrimonioRepository;

    @Autowired
    private TransacaoRepository transacaoRepository;

    BuscarConta buscarConta = new BuscarConta();

    @Transactional
    public void alterarValorConta(Integer idcliente, Integer idConta, ContaMutationDto novoValor){

        Optional<Cliente> cliente = clienteRepository.findById(idcliente);

        if (cliente.isEmpty()){
            throw new EntidadeNaoEncontradaException("Objeto cliente", cliente);
        }
        BuscarConta buscarConta = new BuscarConta();

        Optional<Patrimonio> patrimonio = patrimonioRepository.findById(cliente.get().getPatrimonio().getId());


        Conta contaFiltrada = buscarConta.filtrarContaPorCliente(patrimonio.get(), idConta);


        contaFiltrada.setValorDisponivel(novoValor.getValorDisponivel());
    }

    @Transactional
    public  void transferenciaInternaDeContas(Integer idCliente, Integer idContaSaida, Integer idContaEntrada, TransacaoCriacaoDto transacaoCriacaoDto){

        Optional<Cliente> cliente = clienteRepository.findById(idCliente);

        if (cliente.isEmpty()){
            throw new EntidadeNaoEncontradaException("Objeto cliente", cliente);
        }

        Optional<Patrimonio> patrimonio = patrimonioRepository.findById(cliente.get().getPatrimonio().getId());

        Conta contaSaida = buscarConta.filtrarContaPorCliente(patrimonio.get(), idContaSaida);
        Double valorDisponivel = contaSaida.getValorDisponivel();
        Double valorTransferencia = transacaoCriacaoDto.getValorTransferencia();

        if ( valorTransferencia > valorDisponivel){
            throw new EntidadeNaoProcessavelException(valorDisponivel);
        }

        contaSaida.setValorDisponivel(valorDisponivel - valorTransferencia);
        Conta contaEntrada = buscarConta.filtrarContaPorCliente(patrimonio.get(), idContaEntrada);

        Double valorDisponivelContaEntrada = contaEntrada.getValorDisponivel();

        contaEntrada.setValorDisponivel(valorDisponivelContaEntrada + valorTransferencia);

        Transacao novaTransacao = new Transacao();

        novaTransacao.setDataTransacao(OffsetDateTime.now());
        novaTransacao.setIdContaSaida(idContaSaida);
        novaTransacao.setValorTransferencia(valorTransferencia);
        novaTransacao.setIdcontaEntrada(idContaEntrada);
        transacaoRepository.saveAndFlush(novaTransacao);
    }
    @Transactional(Transactional.TxType.REQUIRED)
    public void transferenciaEntreContas(Integer idClienteSaida,
                                         Integer idContaSaida,
                                         Integer idClienteEntrada,
                                         Integer idContaEntrada,
                                         TransacaoCriacaoDto valorTransferencia) {


        Optional<Cliente> clienteSaida = clienteRepository.findById(idClienteSaida);
        Optional<Cliente> clienteEntrada = clienteRepository.findById(idClienteEntrada);

        if (clienteSaida.isEmpty()){
            throw new EntidadeNaoEncontradaException("Id do cliente", clienteEntrada.get());
        }
        if (clienteEntrada.isEmpty()){
            throw new EntidadeNaoEncontradaException("Id do cliente", clienteEntrada.get());
        }

        Conta contaSaida = buscarConta.filtrarContaPorCliente(clienteSaida.get().getPatrimonio(), idContaSaida);
        Conta contaEntrada = buscarConta.filtrarContaPorCliente(clienteEntrada.get().getPatrimonio(), idContaEntrada);

        if (valorTransferencia.getValorTransferencia() > contaSaida.getValorDisponivel()){
            throw new EntidadeNaoProcessavelException(contaSaida);
        }
        contaSaida.setValorDisponivel(contaSaida.getValorDisponivel() - valorTransferencia.getValorTransferencia());
        contaEntrada.setValorDisponivel(contaEntrada.getValorDisponivel() + valorTransferencia.getValorTransferencia());

    }
}
