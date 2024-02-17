package com.trimix.backendpersonas.exception;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.MappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Objects;

@org.springframework.web.bind.annotation.ControllerAdvice
@Slf4j
public class ControllerAdvice {

    @Autowired
    private Environment environment;

    /**
     * Manejo de excepción generales de la aplicación..
     * @param ex parámetro excepción.
     * @return retorna un objeto de tipo InfoErrorExcepcion, seteando el mensaje de error.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> excepcionGeneral(Exception ex){
        InfoErrorException infoError = new InfoErrorException(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
        log.error(infoError.getMensaje(), ex);
        return new ResponseEntity<>(infoError, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Manejo de excepción para cuando no se cumple una validación utilizando anotaciones.
     * @param ex parámetro excepción.
     * @return  retorna un objeto de tipo InfoErrorExcepcion, seteando las validaciones que no se cumplieron en la solicitud.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> excepcionValidaciones(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        ValidacionesException validationException = new ValidacionesException();
        bindingResult.getFieldErrors().forEach(fieldError -> {
            String fieldName = fieldError.getField();
            if (Objects.nonNull(fieldError.getDefaultMessage()))
                validationException.getErrores().put(fieldName, environment.getProperty(fieldError.getDefaultMessage()));
        });
        validationException.setCodigoError(HttpStatus.BAD_REQUEST.value());
        log.error(validationException.getErrores().toString());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validationException.getErrores());
    }

    /**
     * Manejo de excepción para cuando existe un error de conversion utilizando model mapper.
     * @param ex parámetro excepción.
     * @return retorna un objeto de tipo InfoErrorExcepcion, seteando un mensaje descriptivo del error.
     */
    @ExceptionHandler(MappingException.class)
    public ResponseEntity<Object> modelMapperExcepcion(MappingException ex){
        String causaError = Objects.nonNull(ex.getCause()) ? ex.getCause().getMessage() : "";
        InfoErrorException infoError = new InfoErrorException(HttpStatus.NOT_FOUND.value(), causaError);
        log.error(infoError.getMensaje(), ex);
        return new ResponseEntity<>(infoError, HttpStatus.NOT_FOUND);
    }

    /**
     * Manejo de excepción persona no encontrada.
     * @param ex parámetro excepción.
     * @return retorna un objeto de tipo InfoErrorExcepcion, seteando un mensaje.
     */
    @ExceptionHandler(PersonaNoEncontradaException.class)
    public ResponseEntity<Object> excepcionItemNoEncontrado(PersonaNoEncontradaException ex){
        InfoErrorException infoError = new InfoErrorException(ex.getStatus().value(),environment.getProperty("persona.notfound")+ex.getMensaje());
        log.error(infoError.getMensaje(), ex);
        return new ResponseEntity<>(infoError, HttpStatus.NOT_FOUND);
    }

}
