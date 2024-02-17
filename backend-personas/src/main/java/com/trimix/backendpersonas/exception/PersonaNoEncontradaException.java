package com.trimix.backendpersonas.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class PersonaNoEncontradaException extends  RuntimeException{
    private final HttpStatus status;
    private final String mensaje;

    public PersonaNoEncontradaException(HttpStatus status, String mensaje){
        super(mensaje);
        this.status= status;
        this.mensaje = mensaje;
    }
}
