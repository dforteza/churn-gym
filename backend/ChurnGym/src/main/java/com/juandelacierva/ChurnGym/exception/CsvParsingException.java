package com.juandelacierva.ChurnGym.exception;

// Error de parseo de Csv
public class CsvParsingException extends RuntimeException 
{
    public CsvParsingException(String message) 
    {
        super(message);
    }
}
