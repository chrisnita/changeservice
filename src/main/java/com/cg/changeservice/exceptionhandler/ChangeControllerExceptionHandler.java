package com.cg.changeservice.exceptionhandler;

import org.springframework.core.convert.ConversionFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ChangeControllerExceptionHandler {

    @ExceptionHandler(ConversionFailedException.class)
    public ResponseEntity<String> handleConflict(ConversionFailedException ex) {
        if(ex.getTargetType().getObjectType().equals(Boolean.class))
            return new ResponseEntity<>("isRequestingMostAmountOfCoins is a boolean. Please input true or false or leave blank", HttpStatus.BAD_REQUEST);
        else
            return new ResponseEntity<>("Unknown Bill. Acceptable bills are (1, 2, 5, 10, 20, 50, 100). Please input a correct bill", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleConflict(IllegalArgumentException ex) {
        return new ResponseEntity<>("Unknown Bill. Acceptable bills are (1, 2, 5, 10, 20, 50, 100). Please input a correct bill", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public void handleMissingParams(MissingServletRequestParameterException ex) {
        String name = ex.getParameterName();
        System.out.println(name + " parameter is missing");
    }

    @ExceptionHandler(NotEnoughCoinsException.class)
    public ResponseEntity<String> handleConflict(NotEnoughCoinsException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoSuchMethodException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String>  handleNoSuchEntityException(final HttpServletRequest req, final Exception ex) {
        return new ResponseEntity<>("Requested resource not found", HttpStatus.NOT_FOUND);
    }
}

