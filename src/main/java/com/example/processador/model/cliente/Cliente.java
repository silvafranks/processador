package com.example.processador.model.cliente;

import com.example.processador.model.patrimonio.Patrimonio;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.loader.entity.CascadeEntityJoinWalker;

import javax.persistence.*;
import javax.transaction.Transactional;
import javax.validation.constraints.Email;

@Getter
@Setter
@Entity
@Table(name = "Cliente")
@Data
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String sobrenome;

    @Email
    private String email;

    private Integer cep;

    @OneToOne(cascade = CascadeType.PERSIST)
    private Patrimonio patrimonio;

}
