package com.example.processador.model.transacao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, BigInteger> {
    @Query("select t from Transacao t where t.contaEntrada.idConta = ?1 or t.contaSaida.idConta = ?1")
    List<Transacao> findAllTransacoesConta(Integer idConta);


}
