package de.hsrm.mi.swtpro.pflamoehus.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ProductApiException extends RuntimeException{

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public ProductApiException(String msg){
        super(msg);
    }
}