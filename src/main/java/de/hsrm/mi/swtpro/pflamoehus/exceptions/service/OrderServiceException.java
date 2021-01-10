package de.hsrm.mi.swtpro.pflamoehus.exceptions.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * OrderServiceException for errors in the OrderSerivce classes.
 * 
 * @author Svenja Schenk
 * @version 1
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class OrderServiceException extends RuntimeException{

    
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public OrderServiceException(String message) {
        super(message);
    }
    
}
