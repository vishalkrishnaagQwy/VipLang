package org.lang.exceptions;

public class VipCompilerException extends Exception{
    public VipCompilerException(String message){
        this(message,null,null);
    }

    public VipCompilerException(String message, Integer line, Integer col){
        super("[exception] : "+message+" , line : "+line+" , col : "+col);
    }

}
