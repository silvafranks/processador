package com.example.processador.model.cliente.dto;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import java.io.Serializable;

@Data
@Setter
@Getter
public class ClienteCriacaoDto implements Serializable {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String sobrenome;

    @Email
    private String email;

    private Integer cep;

    @Column
    private String senha;

    @Column
    private String validarSenha;
}
