package de.hsrm.mi.swtpro.pflamoehus.exceptions.service;

public class CreditcardServiceException extends RuntimeException {
    
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * Method for throwing a exception with a custom message.
     * 
     * @param msg message that gets printed
     */
    public CreditcardServiceException(String msg) {
        super(msg);
    }

    /**
     * Default message.
     */
    public CreditcardServiceException() {
        super("Exception occured while trying to access or save repository data.");
    }
}
