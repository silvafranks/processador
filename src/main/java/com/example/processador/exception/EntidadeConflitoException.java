package com.example.processador.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class EntidadeConflitoException  extends RuntimeException {

    public EntidadeConflitoException(Object entidade, String mensagem){
        super(String.format("%s Já existe (Conflito): %s",entidade.toString(), mensagem));
    }
}
