package com.example.processador.config.authetication;

import com.example.processador.config.jwt.JwtService;
import com.example.processador.exception.EntidadeNaoEncontradaException;
import com.example.processador.model.cliente.Cliente;
import com.example.processador.model.cliente.ClienteRepository;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationUtils {
    @Autowired
    private JwtService jwtService;
    @Autowired
    private ClienteRepository clienteRepository;

    public void verificaIdCliente(Integer idCliente, String token){
        String email = jwtService.extractClaim(token.substring(7), Claims::getSubject);
        Optional<Cliente> byEmail = clienteRepository.findByEmail(email);


        if (!byEmail.get().getId().equals(idCliente)){
            throw new EntidadeNaoEncontradaException("Cliente não encontrado", idCliente);
        }
    }
}
