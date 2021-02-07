package de.hsrm.mi.swtpro.pflamoehus.exceptions.service;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

/**
 * EmailServiceException for errors in the PasswordRequestService classes.
 * 
 * @author Sarah Wenzel
 * @version 1
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EmailServiceException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * Method for throwing a exception with a custom message.
     * 
     * @param msg message that gets printed
     */
    public EmailServiceException(String msg) {
        super(msg);
    }

    /**
     * Default message.
     */
    public EmailServiceException() {
        super("Exception occured while trying to access or save repository data.");
    }
}
