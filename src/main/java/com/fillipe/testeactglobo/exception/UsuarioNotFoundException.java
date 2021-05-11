package com.fillipe.testeactglobo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UsuarioNotFoundException extends Exception{
    public UsuarioNotFoundException(String id) {
        super(String.format("Usuario com ID %s não encontrado! Por favor verifique o numero digitado", id));
    }
}
