package com.example.processador.model.cliente;

import com.example.processador.model.conta.Conta;
import com.example.processador.model.patrimonio.Patrimonio;
import com.example.processador.model.transacao.Transacao;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Formula;
import org.hibernate.loader.entity.CascadeEntityJoinWalker;

import javax.persistence.*;
import javax.transaction.Transactional;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "Cliente")
@Data

public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    @NotNull
    private String name;

    @Column
    private String senha;

    @Column(nullable = false)
    private String sobrenome;

    @Email
    @Column(nullable = false,unique = true)
    private String email;

    @Column(nullable = false)
    private Integer cep;

    @Formula("SELECT sum(contas.valor_disponivel) FROM CONTAS WHERE contas.cliente_id = id AND contas.flag ='Ativo'")
    private BigDecimal totalPatrimonio;

}
