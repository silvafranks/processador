package com.example.processador.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

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
}
