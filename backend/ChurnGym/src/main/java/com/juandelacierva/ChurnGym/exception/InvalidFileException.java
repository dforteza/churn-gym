package com.juandelacierva.ChurnGym.exception;


// Fichero Invalido: Formato incorrecto o vacio
public class InvalidFileException extends RuntimeException 
{
    public InvalidFileException(String message) 
    {
        super(message);
    }
}
