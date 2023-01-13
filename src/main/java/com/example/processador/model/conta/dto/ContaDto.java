package com.example.processador.model.conta.dto;

import com.example.processador.model.conta.dto.Banco;
import lombok.Data;

import javax.validation.constraints.Past;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
public class ContaDto implements Serializable {


    private Integer idConta;
    private Banco banco;
    private BigDecimal valorDisponivel;
    @Past
    private OffsetDateTime ultimaMovimentacao;
}
