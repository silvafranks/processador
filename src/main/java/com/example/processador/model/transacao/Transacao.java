package com.example.processador.model.transacao;

import com.example.processador.model.cliente.Cliente;
import com.example.processador.model.conta.Conta;
import com.example.processador.model.transacao.Dto.TypeTransacao;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
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
    @Enumerated(EnumType.STRING)
    private TypeTransacao type;

    @Column
    @NotNull
    private OffsetDateTime dataTransacao;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn( name = "contaEntradaId")
    private Conta contaEntrada;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "contaSaidaId")
    private Conta contaSaida;

}

