package com.example.processador.model.patrimonio;

import com.example.processador.model.conta.Conta;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
@Entity
@Data
@AllArgsConstructor
@Table(name = "Patrimonios")
public class Patrimonio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

//    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "patrimonio")
//    @JsonIgnoreProperties
//    private List<Conta> contas;
//
//    @Formula("SELECT sum(conta.valor_disponivel) FROM CONTA WHERE conta.patrimonio_id = id")
//    private BigDecimal totalPatrimonio;
//TODO - RELACIONAR CONTAS DIRETO COM CLIENTE
    public Patrimonio(){}

}
