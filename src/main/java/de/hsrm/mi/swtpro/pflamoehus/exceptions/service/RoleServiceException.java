package de.hsrm.mi.swtpro.pflamoehus.exceptions.service;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

/**
 * RoleServiceExceptions for errors in the RoleService classes.
 * 
 * @author Ann-Cathrin Fabian
 * @version 1
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RoleServiceException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * Method for throwing a exception with a custom message.
     * 
     * @param msg message that gets printed
     */
    public RoleServiceException(String msg) {
        super(msg);
    }

    /**
     * Default massage.
     */
    public RoleServiceException() {
        super("Exception occured while trying to access or save repository data.");
    }
}
