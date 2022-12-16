package com.example.processador.model.transacao;

import com.example.processador.model.conta.Conta;
import com.example.processador.model.conta.dto.Banco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;

@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, BigInteger> {
    @Query("select distinct t from Transacao t where t.contaEntrada.idConta = ?1 or t.contaSaida.idConta = ?1")
    List<Transacao> findAllTransacoesConta(Integer idConta);


    @Transactional
    @Modifying
    @Query("delete from Transacao t where t.idTransacao = ?1")
    int deleteByIdTransacao(Long idTransacao);

    @Transactional
    @Modifying
    @Query("delete from Transacao t where t.contaEntrada = ?1 or t.contaSaida = ?1")
    int deleteByContaEntradaAndContaSaida(Conta contaEntrada);

    @Query("select t from Transacao t where t.contaEntrada = ?1")
    List<Transacao> findByContaEntrada(Conta contaEntrada);

}
