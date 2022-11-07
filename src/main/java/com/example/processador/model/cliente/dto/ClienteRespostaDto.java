package com.example.processador.model.cliente.dto;

import com.example.processador.model.patrimonio.Patrimonio;
import lombok.Data;
import org.hibernate.annotations.Formula;

import javax.persistence.Column;
import javax.persistence.OneToOne;
import javax.validation.constraints.Email;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class ClienteRespostaDto implements Serializable {

    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String sobrenome;

    @Email
    private String email;

    @Column(nullable = false)
    private Integer cep;

    @Formula("SELECT sum(contas.valor_disponivel) FROM CONTAS WHERE conta.cliente_id = id")
    private BigDecimal totalPatrimonio;

}
