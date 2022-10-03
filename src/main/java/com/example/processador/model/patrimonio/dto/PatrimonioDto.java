package com.example.processador.model.patrimonio.dto;

import com.example.processador.model.conta.Conta;
import lombok.Data;
import org.hibernate.annotations.Formula;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
public class PatrimonioDto implements Serializable {

    private  List<Conta> contas;


}