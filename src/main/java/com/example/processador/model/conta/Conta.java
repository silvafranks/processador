package com.example.processador.model.conta;

import com.example.processador.model.patrimonio.Patrimonio;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Past;
import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.Optional;

@Getter
@Setter
@Entity
@Data
@Table(name = "conta")
public class Conta implements Comparable<Conta> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idConta;

    @Column(nullable = false)
    private String nomeFantasia;

    @Column(nullable = false)
    private Double valorDisponivel;

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
