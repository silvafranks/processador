package com.example.processador.model.conta.services;

import com.example.processador.Utils.ContaUtils;
import com.example.processador.config.authetication.AuthenticationUtils;
import com.example.processador.exception.EntidadeNaoEncontradaException;
import com.example.processador.exception.EntidadeNaoProcessavelException;
import com.example.processador.model.cliente.Cliente;
import com.example.processador.model.cliente.ClienteRepository;
import com.example.processador.model.conta.Conta;
import com.example.processador.model.conta.ContaRepository;
import com.example.processador.model.transacao.Dto.TransacaoCriacaoDto;
import com.example.processador.model.transacao.Dto.TypeTransacao;
import com.example.processador.model.transacao.TransacaoRepository;
import com.example.processador.model.transacao.service.TransacaoServiceCreate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
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
    private TransacaoRepository transacaoRepository;

    @Autowired
    private TransacaoServiceCreate transacaoServiceCreate;

    @Autowired
    private ContaUtils buscarConta ;
    @Autowired
    private AuthenticationUtils authenticationUtils;

    @Transactional
    public void depositar(Integer idCliente, Integer idConta, TransacaoCriacaoDto novoValor, String token){
        authenticationUtils.verificaIdCliente(idCliente, token);

        Optional<Cliente> cliente = clienteRepository.findById(idCliente);

        if (cliente.isEmpty()){
            throw new EntidadeNaoEncontradaException("Objeto cliente", cliente);
        }

        Conta contaFiltrada = buscarConta.filtrarContaPorCliente(cliente.get(), idConta);

        System.out.println(contaFiltrada.getValorDisponivel());

        contaFiltrada.setValorDisponivel(contaFiltrada.getValorDisponivel().add(novoValor.getValorTransferencia()));

        contaFiltrada.setUltimaMovimentacao(OffsetDateTime.now());

        TransacaoCriacaoDto baseTransacao = new TransacaoCriacaoDto();
        baseTransacao.setTypeTransacao(TypeTransacao.deposito);
        baseTransacao.setDataTransacao(OffsetDateTime.now());
        baseTransacao.setValorTransferencia(novoValor.getValorTransferencia());

        transacaoServiceCreate.criarTransacao(baseTransacao,contaFiltrada,null);
    }

    @Transactional
    public  void transferenciaInternaDeContas(Integer idCliente, Integer idContaSaida, Integer idContaEntrada, TransacaoCriacaoDto transacaoCriacaoDto, String token){
        authenticationUtils.verificaIdCliente(idCliente, token);
        Optional<Cliente> cliente = clienteRepository.findById(idCliente);

        if (cliente.isEmpty()){
            throw new EntidadeNaoEncontradaException("Objeto cliente", cliente);
        }

        Conta contaSaida = buscarConta.filtrarContaPorCliente(cliente.get(), idContaSaida);
        BigDecimal valorDisponivel = contaSaida.getValorDisponivel();
        BigDecimal valorTransferencia = transacaoCriacaoDto.getValorTransferencia();

        if ( valorTransferencia.compareTo(valorDisponivel)> 0){
            throw new EntidadeNaoProcessavelException(valorDisponivel);
        }

        contaSaida.setValorDisponivel(valorDisponivel.subtract(valorTransferencia));
        Conta contaEntrada = buscarConta.filtrarContaPorCliente(cliente.get(), idContaEntrada);


        BigDecimal valorDisponivelContaEntrada = contaEntrada.getValorDisponivel();

        contaEntrada.setValorDisponivel(valorDisponivelContaEntrada.add(valorTransferencia));

        contaEntrada.setUltimaMovimentacao(transacaoCriacaoDto.getDataTransacao());

        contaSaida.setUltimaMovimentacao(transacaoCriacaoDto.getDataTransacao());

        TransacaoCriacaoDto baseTransacao = new TransacaoCriacaoDto();

        baseTransacao.setTypeTransacao(TypeTransacao.Interno);
        baseTransacao.setDataTransacao(OffsetDateTime.now());
        baseTransacao.setValorTransferencia(transacaoCriacaoDto.getValorTransferencia());

        transacaoServiceCreate.criarTransacao(baseTransacao,contaEntrada,contaSaida);

    }
    @Transactional
    public void transferenciaEntreClientes(Integer idClienteSaida,
                                           Integer idContaSaida,
                                           Integer idClienteEntrada,
                                           Integer idContaEntrada,
                                           TransacaoCriacaoDto valorTransferencia, String token) {

        authenticationUtils.verificaIdCliente(idClienteSaida, token);

        Optional<Cliente> clienteSaida = clienteRepository.findById(idClienteSaida);
        Optional<Cliente> clienteEntrada = clienteRepository.findById(idClienteEntrada);

        if (clienteSaida.isEmpty()){
            throw new EntidadeNaoEncontradaException("Id do cliente", clienteEntrada.get());
        }
        if (clienteEntrada.isEmpty()){
            throw new EntidadeNaoEncontradaException("Id do cliente", clienteEntrada.get());
        }

        Conta contaSaida = buscarConta.filtrarContaPorCliente(clienteSaida.get(), idContaSaida);
        Conta contaEntrada = buscarConta.filtrarContaPorCliente(clienteEntrada.get(), idContaEntrada);

        if (valorTransferencia.getValorTransferencia().compareTo(contaSaida.getValorDisponivel()) > 0 ){
            throw new EntidadeNaoProcessavelException(contaSaida);
        }

        contaSaida.setValorDisponivel(contaSaida.getValorDisponivel().subtract(valorTransferencia.getValorTransferencia()));
        contaEntrada.setValorDisponivel(contaEntrada.getValorDisponivel().add(valorTransferencia.getValorTransferencia()));

        contaSaida.setUltimaMovimentacao(valorTransferencia.getDataTransacao());
        contaEntrada.setUltimaMovimentacao(valorTransferencia.getDataTransacao());

        TransacaoCriacaoDto baseTransacao = new TransacaoCriacaoDto();

        baseTransacao.setTypeTransacao(TypeTransacao.Externo);
        baseTransacao.setDataTransacao(OffsetDateTime.now());
        baseTransacao.setValorTransferencia(valorTransferencia.getValorTransferencia());

        transacaoServiceCreate.criarTransacao(baseTransacao,contaEntrada,contaSaida);

    }

    @Transactional
    public void retirar(Integer idCliente, Integer idConta, TransacaoCriacaoDto valor,String token) {
        authenticationUtils.verificaIdCliente(idCliente, token);

        Optional<Cliente> clienteSaida = clienteRepository.findById(idCliente);

        if (clienteSaida.isEmpty()){
            throw new EntidadeNaoEncontradaException("Id do cliente", clienteSaida.get());
        }
        Conta contaSaida = buscarConta.filtrarContaPorCliente(clienteSaida.get(), idConta);

        contaSaida.setValorDisponivel(contaSaida.getValorDisponivel().subtract(valor.getValorTransferencia()));

        contaSaida.setUltimaMovimentacao(valor.getDataTransacao());

    }
}
