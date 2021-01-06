package de.hsrm.mi.swtpro.pflamoehus.exceptions;

public class OrderDetailsServiceException extends RuntimeException{

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * Method for throwing a exception with a custom message.
     * 
     */
    public OrderDetailsServiceException() {
        super("Exception occured while trying to access or save repository data.");
    }

    /**
     * Method for throwing a exception with a custom message.
     * 
     * @param msg message that gets printed
     */
    public OrderDetailsServiceException(String msg) {
        super(msg);
    }
}
