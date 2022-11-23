package com.example.processador.model.conta.services;

import com.example.processador.Utils.ContaUtils;
import com.example.processador.exception.EntidadeNaoEncontradaException;
import com.example.processador.model.cliente.Cliente;
import com.example.processador.model.cliente.ClienteRepository;
import com.example.processador.model.cliente.dto.ClienteDto;
import com.example.processador.model.cliente.services.ClienteMapper;
import com.example.processador.model.conta.Conta;
import com.example.processador.model.conta.ContaRepository;
import com.example.processador.model.conta.dto.ContaCriacaoDto;
import com.example.processador.model.conta.dto.ContaDto;
import com.example.processador.model.conta.dto.EnumFlag;
import com.example.processador.model.patrimonio.PatrimonioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.transaction.Transactional;
import java.util.Optional;

@Validated
@Service
public class ContaServiceCreate {

    @Autowired
    private ContaRepository contaRepository;

    @Autowired
    private ContaMapper contaMapper;

    @Autowired
    private ClienteMapper clienteMapper;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private PatrimonioRepository patrimonioRepository;

    @Autowired
    private ContaUtils contaUtils ;

    @Transactional
    public void criarConta(Integer idCliente, ContaCriacaoDto contaCriacaoDto) {
        if (clienteRepository.findById(idCliente).isEmpty()) {
            throw new EntidadeNaoEncontradaException("Cliente", contaCriacaoDto);
        }

        Optional<Cliente> cliente = clienteRepository.findById(idCliente);

        ClienteDto clienteDto = clienteMapper.DomaintoClienteDto(cliente.get());

        contaUtils.VerificarBanco(cliente.get(), contaCriacaoDto);

        Conta conta = new Conta();
        conta.setUltimaMovimentacao(contaCriacaoDto.getUltimaMovimentacao());
        conta.setValorDisponivel(contaCriacaoDto.getValorDisponivel());
        conta.setBanco(contaCriacaoDto.getBanco());
        conta.setCliente(cliente.get());
        conta.setFlag(EnumFlag.Ativo);

        contaRepository.save(conta);

    }
}











