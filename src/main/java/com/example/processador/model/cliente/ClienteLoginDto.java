package com.example.processador.model.cliente;

import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import java.io.Serializable;

@Data
public class ClienteLoginDto implements Serializable {
    private Integer id;

    @Email
    @Column(nullable = false,unique = true)
    private String email;
    @Column
    private String senha;

}