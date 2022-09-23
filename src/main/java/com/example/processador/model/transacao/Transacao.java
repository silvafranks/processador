package com.example.processador.model.transacao;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;
import java.time.OffsetDateTime;

@Entity
@Data
@Table(name = "transacoes")
public class Transacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTransacao;

    @Column
    @NotNull
    private Double valorTransferencia;

    @Column
    @NotNull
    private Integer idContaSaida;

    @Column
    @NotNull
    private  Integer idcontaEntrada;

    @Column
    @NotNull
    private OffsetDateTime dataTransacao;
}

