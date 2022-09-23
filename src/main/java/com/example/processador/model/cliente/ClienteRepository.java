package com.example.processador.model.cliente;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente,Integer>, JpaSpecificationExecutor<Cliente> {

    Cliente findById(String id);


//    @Query(value = "select count (valor_diponivel) as totalPatrimonio from conta where conta.id_conta = " +
//            "(select patrimonio_contas.contas_id_conta from cliente join patrimonio_contas on  cliente.patrimonio_cliente_id  = patrimonio_contas.patrimonio_id where cliente.id = :idCliente)")
//    Optional<Integer> totalReceita(@Param("idCliente") Integer idCliente);
}
