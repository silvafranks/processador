package com.example.processador.model.conta.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
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
    private String nomeFantasia;

    @Column(nullable = false)
    @Past
    private OffsetDateTime ultimaMovimentacao;

}