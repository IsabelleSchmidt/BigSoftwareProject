package de.hsrm.mi.swtpro.pflamoehus.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Error message if an email is already in use.
 * 
 * @author Ann-Cathrin Fabian
 * @version 1
 */
public class EmailAlreadyInUseException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    Logger emailAlreadyInUseExceptionLogger = LoggerFactory.getLogger(EmailAlreadyInUseException.class);

    /**
     * Default massage.
     */
    public EmailAlreadyInUseException() {
        emailAlreadyInUseExceptionLogger.error("Email is already in use.");
    }

    /**
     * Method for creating an own error message
     * 
     * @param error -> the given error message
     */
    EmailAlreadyInUseException(String error) {
        emailAlreadyInUseExceptionLogger.error(error);
    }

}
