package de.hsrm.mi.swtpro.pflamoehus.exceptions.service;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

/**
 * OrderDetailsServiceException for errors in the OrderDetailsService classes.
 * 
 * @author Ann-Cathrin Fabian
 * @version 1
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class OrderDetailsServiceException extends RuntimeException{

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * Method for throwing a exception with a custom message.
     * 
     */
    public OrderDetailsServiceException() {
        super("Exception occured while trying to access or save repository data.");
    }

    /**
     * Method for throwing a exception with a custom message.
     * 
     * @param msg message that gets printed
     */
    public OrderDetailsServiceException(String msg) {
        super(msg);
    }
}
