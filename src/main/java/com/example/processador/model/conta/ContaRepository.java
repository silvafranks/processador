package com.example.processador.model.conta;

import com.example.processador.model.cliente.Cliente;
import com.example.processador.model.conta.dto.Banco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContaRepository extends JpaRepository<Conta,Integer> {

    @Query("select c from Conta c where c.cliente = ?1")
    List<Conta> findByCliente(Cliente cliente);

    @Query("select c from Conta c where c.cliente.id = ?1")
    List<Conta> findByCliente_Id(Integer id);

    @Query("select c from Conta c where c.cliente.id = ?1 and c.banco = ?2")
    List<Conta> findByCliente_IdAndBanco(Integer id, Banco banco);




}
