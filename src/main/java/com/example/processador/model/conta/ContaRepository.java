package com.example.processador.model.conta;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContaRepository extends JpaRepository<Conta,Integer> {

//    @Query("select c from Conta c where c.patrimonio.id = ?1")
//    List<Conta> findByPatrimonio_Id(Integer id);
}
