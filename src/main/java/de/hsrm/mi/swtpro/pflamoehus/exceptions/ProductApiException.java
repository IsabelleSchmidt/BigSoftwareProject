package de.hsrm.mi.swtpro.pflamoehus.exceptions;

public class ProductApiException extends RuntimeException{

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public ProductApiException(String msg){
        super(msg);
    }
}