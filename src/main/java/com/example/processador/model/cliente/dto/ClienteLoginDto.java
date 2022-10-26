package com.example.processador.model.cliente.dto;

import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class ClienteLoginDto implements Serializable {
    @Email
    @Column(nullable = false,unique = true)
    private String email;
    @Column
    private String senha;


}
