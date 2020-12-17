package de.hsrm.mi.swtpro.pflamoehus.exceptions;

/**
 * Error message if an email is already in use.
 * 
 * @author Ann-Cathrin Fabian
 * @version 1
 */
public class EmailAlreadyInUseException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * Default massage.
     */
    public EmailAlreadyInUseException() {
        System.out.println("Email is already in use.");
    }

    /**
     * Method for creating an own error message
     * 
     * @param error -> the given error message
     */
    EmailAlreadyInUseException(String error) {
        System.out.println(error);
    }

}
