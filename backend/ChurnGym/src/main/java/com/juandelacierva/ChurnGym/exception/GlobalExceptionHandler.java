package com.juandelacierva.ChurnGym.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler 
{
    // 400 - Validaciones (@Valid) fallidas
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex)
    {
        Map<String, String> errors = new HashMap<>();
        
        ex.getBindingResult().getFieldErrors().forEach(error -> {
                errors.put(error.getField(), error.getDefaultMessage());
            });
        
        Map<String, Object> body = new HashMap<>();
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("error", "Bad Request");
        body.put("message", "Errores de validación");
        body.put("errors", errors);
        
        return (new ResponseEntity<>(body, HttpStatus.BAD_REQUEST));
    }

    // 400 - fichero inválido o vacío
    @ExceptionHandler(InvalidFileException.class)
    public ResponseEntity<Object> handleInvalidFileException(InvalidFileException ex)
    {
        Map<String, Object> body = new HashMap<>();

        body.put("status", HttpStatus.BAD_REQUEST);
        body.put("error", "Bad Request");
        body.put("mesage", ex.getMessage());

        return (new ResponseEntity<>(body, HttpStatus.BAD_REQUEST));
    }

    // 400 - CSV con formato incorrecto
    @ExceptionHandler(CsvParsingException.class)
    public ResponseEntity<Object> handlerCsvParsingException(CsvParsingException ex)
    {
        Map<String, Object> body = new HashMap<>();

        body.put("status", HttpStatus.BAD_REQUEST);
        body.put("error", "Bad Request");
        body.put("mesage", ex.getMessage());

        return (new ResponseEntity<>(body, HttpStatus.BAD_REQUEST));
    }

    // 403 - acceso denegado
    public ResponseEntity<Object> handlerModeloAcessDeniedException(ModeloAcessDeniedException ex)
    {
        Map<String, Object> body = new HashMap<>();

        body.put("status", HttpStatus.FORBIDDEN);
        body.put("error", "Forbidden");
        body.put("mesage", ex.getMessage());

        return (new ResponseEntity<>(body, HttpStatus.FORBIDDEN));
    }

    // 404 - recurso no encontrado
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex)
    {
        Map<String, Object> body = new HashMap<>();

        body.put("status", HttpStatus.NOT_FOUND.value());
        body.put("error", "Not Found");
        body.put("message", ex.getMessage());
        
        return (new ResponseEntity<>(body, HttpStatus.NOT_FOUND));
    }

    // 409 - violacion de constraint en BD (FK, UNIQUE, ...)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handlerDataIntegrityViolation(DataIntegrityViolationException ex)
    {
        Map<String, Object> body = new HashMap<>();
        
        body.put("status", HttpStatus.CONFLICT.value());
        body.put("error", "Conflict");
        body.put("message", "Error de integridad de datos");
        body.put("details", ex.getMostSpecificCause().getMessage());
        
        return (new ResponseEntity<>(body, HttpStatus.CONFLICT));
    }

    // 500 - excepcion generica
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGenericException(Exception ex)
    {
        Map<String, Object> body = new HashMap<>();
        
        body.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        body.put("error", "Internal Server Error");
        body.put("message", "Error interno del servidor");
        
        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }


}