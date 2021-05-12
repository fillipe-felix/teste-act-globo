package com.fillipe.testeactglobo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.NOT_FOUND)
public class UsuarioLocalDateException extends Exception{

    public UsuarioLocalDateException(String message) {

        super(String.format("Por favor, digite uma data menor do que %s", message));

    }
}
