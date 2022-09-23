package com.example.processador.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
public class EntidadeNaoProcessavelException extends RuntimeException{

    public EntidadeNaoProcessavelException(Object entidade){
        super(String.format("%s não encontrada: %s", entidade.toString()));
    }
}
