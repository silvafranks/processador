package com.example.processador.model.cliente;

import com.example.processador.enums.RoleName;
import com.example.processador.model.role.RoleModel;
import lombok.*;
import org.hibernate.annotations.Formula;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.management.relation.Role;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "Cliente")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cliente  implements UserDetails, Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

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

    @Formula("SELECT sum(contas.valor_disponivel) FROM CONTAS WHERE contas.cliente_id = id")
    private BigDecimal totalPatrimonio;


    @Enumerated(EnumType.STRING)
    private RoleName role;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getCep() {
        return cep;
    }

    public void setCep(Integer cep) {
        this.cep = cep;
    }

    public BigDecimal getTotalPatrimonio() {
        return totalPatrimonio;
    }

    public void setTotalPatrimonio(BigDecimal totalPatrimonio) {
        this.totalPatrimonio = totalPatrimonio;
    }
}
