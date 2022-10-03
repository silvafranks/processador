package com.example.processador.model.conta.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@Data
public class ContaMutationDto implements Serializable {
    @Column(nullable = false)
    private BigDecimal valorDisponivel;

}
