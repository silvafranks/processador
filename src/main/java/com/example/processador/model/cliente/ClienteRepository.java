package com.example.processador.model.cliente;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente,Integer>, JpaSpecificationExecutor<Cliente> {
    Cliente findByEmail(String email);
    Optional<Cliente> findById(Integer id);

    @Override
    void deleteById(Integer integer);


}
