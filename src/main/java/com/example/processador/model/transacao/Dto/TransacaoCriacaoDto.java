package com.example.processador.model.transacao.Dto;

import com.example.processador.model.transacao.Transacao;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
public class TransacaoCriacaoDto implements Serializable {
    @NotBlank
    private BigDecimal valorTransferencia;
    @NotBlank
    private  OffsetDateTime dataTransacao;

    @NotBlank
    private TypeTransacao typeTransacao;
}