package de.hsrm.mi.swtpro.pflamoehus.exceptions;

public class BankcardServiceException extends RuntimeException{

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * Method for throwing a exception with a custom message.
     * 
     * @param msg message that gets printed
     */
    public BankcardServiceException(String msg) {
        super(msg);
    }

    /**
     * Default message.
     */
    public BankcardServiceException() {
        super("Exception occured while trying to access or save repository data.");
    }
    
}
