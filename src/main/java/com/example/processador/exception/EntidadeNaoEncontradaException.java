package com.example.processador.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class EntidadeNaoEncontradaException extends RuntimeException {

    public EntidadeNaoEncontradaException(String mensagem,Object entidade){
        super(String.format("%s não encontrada: %s", entidade.toString(),mensagem));
    }
}
