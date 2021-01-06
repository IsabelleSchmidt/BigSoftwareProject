package de.hsrm.mi.swtpro.pflamoehus.exceptions.service;

public class AdressServiceException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * Method for throwing a exception with a custom message.
     * 
     * @param msg message that gets printed
     */
    public AdressServiceException(String msg) {
        super(msg);
    }

    /**
     * Default message.
     */
    public AdressServiceException() {
        super("Exception occured while trying to access or save repository data.");
    }
    
}
