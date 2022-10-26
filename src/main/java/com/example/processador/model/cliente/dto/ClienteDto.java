package com.example.processador.model.cliente.dto;

import com.example.processador.model.patrimonio.Patrimonio;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.OneToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class ClienteDto implements Serializable {

    @Column(nullable = false)
    @NotNull
    private String name;

    @NotNull
    @Column(nullable = false)
    private String sobrenome;

    @NotNull
    @Email
    private String email;

    @NotNull
    @Column(nullable = false)
    private Integer cep;


}
