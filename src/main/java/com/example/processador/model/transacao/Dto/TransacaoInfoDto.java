package com.example.processador.model.transacao.Dto;

import com.example.processador.model.conta.dto.Banco;
import lombok.Data;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
public class TransacaoInfoDto {
    private BigDecimal valorTransferencia;
    private OffsetDateTime dataTransacao;
    private TypeTransacao typeTransacao;
    private Integer idCliente;
    private Banco ContaSaida;
    private Banco ContaEntrada;
}
