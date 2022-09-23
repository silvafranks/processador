package com.example.processador.model.conta.dto;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.OffsetDateTime;

public class ContaRespostaDto implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idConta;

    @Column(nullable = false)
    private String nomeFantasia;

    @Column(nullable = false)
    private Integer valorDisponivel;

    @Column(nullable = false)
    private OffsetDateTime ultimaMovimentacao;
}
