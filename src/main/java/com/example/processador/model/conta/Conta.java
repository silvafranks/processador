package com.example.processador.model.conta;

import com.example.processador.model.cliente.Cliente;
import com.example.processador.model.conta.dto.Banco;
import com.example.processador.model.patrimonio.Patrimonio;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Past;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Optional;
@Entity
@Data
@Table(name = "contas")
public class Conta implements Comparable<Conta> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idConta;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Banco banco;

    @Column(nullable = false)
    private BigDecimal valorDisponivel;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id",nullable = false)
    private Cliente cliente;

    @Column(nullable = false)
    @Past
    private OffsetDateTime ultimaMovimentacao;


    @Override
    public int compareTo(Conta conta) {
        if (this.ultimaMovimentacao.isBefore(conta.ultimaMovimentacao) ) {
            return -1;
        } if (this.ultimaMovimentacao.isAfter(conta.ultimaMovimentacao)) {
            return 1;
        }
        return 0;
    }

}
