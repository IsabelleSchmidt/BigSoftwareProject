package de.hsrm.mi.swtpro.pflamoehus.exceptions;

public class EmailOrPasswordWrongException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /**
     * Method for throwing a exception with a custom message.
     * 
     * @param msg the message that has to be printed
     */
    public EmailOrPasswordWrongException() {
        super("Password or Email wrong.");
    }
}
