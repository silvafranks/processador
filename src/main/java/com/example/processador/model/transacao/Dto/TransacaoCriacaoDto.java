package com.example.processador.model.transacao.Dto;

import com.example.processador.model.transacao.Transacao;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.OffsetDateTime;

/**
 * A DTO for the {@link Transacao} entity
 */
@Data
public class TransacaoCriacaoDto implements Serializable {
    @NotBlank
    private  Double valorTransferencia;
    @NotBlank
    private  OffsetDateTime dataTransacao;
}