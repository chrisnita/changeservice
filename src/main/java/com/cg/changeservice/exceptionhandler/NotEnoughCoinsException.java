package com.cg.changeservice.exceptionhandler;

public class NotEnoughCoinsException extends Exception{
    public static final long serialVersionUID = 1L;
    public NotEnoughCoinsException(String cause){
        super(cause);
    }
}
