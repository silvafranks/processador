package com.example.processador.model.cliente.dto;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class ClienteDto implements Serializable {

    @NotNull
    private String name;

    @NotNull
    private String sobrenome;

    @NotNull
    private String email;

    @NotNull
    private Integer cep;




}
