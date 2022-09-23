package com.example.processador.model.transacao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, BigInteger> {
}
