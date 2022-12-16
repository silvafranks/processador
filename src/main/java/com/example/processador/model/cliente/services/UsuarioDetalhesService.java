package com.example.processador.model.cliente.services;

import com.example.processador.model.cliente.Cliente;
import com.example.processador.model.cliente.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
@Service
public class UsuarioDetalhesService implements UserDetailsService {

    @Autowired
    private ClienteRepository repository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Cliente cliente = repository.findByEmail(username).orElseThrow(
                () -> new UsernameNotFoundException("User not found with username or email : " + username)
        );

        return new User(cliente.getEmail(), cliente.getSenha(), Collections.emptyList());
    }
}
