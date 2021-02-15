package de.hsrm.mi.swtpro.pflamoehus.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

/**
 * Error message if an email is already in use.
 * 
 * @author Ann-Cathrin Fabian
 * @version 1
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EmailAlreadyInUseException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    static final Logger emailAlreadyInUseExceptionLogger = LoggerFactory.getLogger(EmailAlreadyInUseException.class);

    /**
     * Default massage.
     */
    public EmailAlreadyInUseException() {
        emailAlreadyInUseExceptionLogger.error("Diese Email ist schon vergeben");
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
