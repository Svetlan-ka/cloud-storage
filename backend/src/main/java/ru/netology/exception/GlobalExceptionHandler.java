package ru.netology.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(InvalidDataException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED) //401
    public Error handleInvalidDataException(InvalidDataException e){
        return new Error(e.getMessage(), HttpStatus.UNAUTHORIZED.value());
    }
    @ExceptionHandler(InvalidJwtException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED) //401
    public Error handleInvalidJwtException(InvalidJwtException e){
        return new Error(e.getMessage(), HttpStatus.UNAUTHORIZED.value());
    }
    @ExceptionHandler(FileNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND) //404
    public Error handleFileNotFoundException(FileNotFoundException e){
        return new Error(e.getMessage(), HttpStatus.NOT_FOUND.value());
    }
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND) //404
    public Error handleUserNotFoundException(UserNotFoundException e){
        return new Error(e.getMessage(), HttpStatus.NOT_FOUND.value());
    }
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST) //400
    public Error handleMaxUploadSizeExceeded(MaxUploadSizeExceededException e){
        return new Error(e.getMessage(), HttpStatus.BAD_REQUEST.value());
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST) //400
    public Error handleBadCredentialsException(BadCredentialsException e){
        return new Error(e.getMessage(), HttpStatus.BAD_REQUEST.value());
    }



}