package com.example.processador.model.transacao;

import com.example.processador.model.cliente.Cliente;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
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
    private BigDecimal valorTransferencia;

    @Column
    private Integer idContaSaida;

    @Column
    @NotNull
    private  Integer idcontaEntrada;

    @Column
    @NotNull
    private OffsetDateTime dataTransacao;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    @JsonIgnore
    @JsonIgnoreProperties
    private Cliente cliente;
}

