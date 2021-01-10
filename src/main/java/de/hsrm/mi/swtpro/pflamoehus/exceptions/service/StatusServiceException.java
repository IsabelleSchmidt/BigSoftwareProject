package de.hsrm.mi.swtpro.pflamoehus.exceptions.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * StatusServiceException for errors in the StatusSerivce classes.
 * 
 * @author Svenja Schenk
 * @version 1
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class StatusServiceException extends RuntimeException{

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * Method for throwing a exception with a custom message.
     * 
     * @param msg message that gets printed
     */
    public StatusServiceException(String msg){
        super(msg);
    }
    
}
