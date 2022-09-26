package com.example.processador.model.conta.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Past;
import javax.validation.constraints.PastOrPresent;
import java.io.Serializable;
import java.time.OffsetDateTime;

@Getter
@Setter
@Data
public class ContaCriacaoDto implements Serializable {


    @Column(nullable = false)
    private Double valorDisponivel;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Banco banco;

    @Column(nullable = false)
    @Past
    private OffsetDateTime ultimaMovimentacao;

}