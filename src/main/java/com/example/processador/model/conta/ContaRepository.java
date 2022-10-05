package com.example.processador.model.conta;

import com.example.processador.model.cliente.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContaRepository extends JpaRepository<Conta,Integer> {

    @Query("select c from Conta c where c.cliente = ?1")
    List<Conta> findByCliente(Cliente cliente);

    @Query("select c from Conta c inner join c.cliente.conta conta where c.cliente = ?1 and conta.idConta = ?2")
    List<Conta> findByClienteAndCliente_Conta_IdConta(Cliente cliente, Integer idConta);

}
