package com.example.processador.model.cliente.dto;

import com.example.processador.model.patrimonio.Patrimonio;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.OneToOne;
import javax.validation.constraints.Email;
import java.io.Serializable;

@Data
public class ClienteRespostaDto implements Serializable {


    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String sobrenome;

    @Email
    private String email;

    @Column(nullable = false)
    private Integer cep;

}
