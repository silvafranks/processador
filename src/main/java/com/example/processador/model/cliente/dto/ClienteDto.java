package com.example.processador.model.cliente.dto;

import com.example.processador.model.conta.Conta;
import com.example.processador.model.conta.dto.ContaDto;
import com.example.processador.model.patrimonio.Patrimonio;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
