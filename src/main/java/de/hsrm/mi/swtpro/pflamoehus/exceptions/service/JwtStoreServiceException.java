package de.hsrm.mi.swtpro.pflamoehus.exceptions.service;

public class JwtStoreServiceException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * Method for throwing a exception with a custom message.
     * 
     * @param msg message that gets printed
     */
    public JwtStoreServiceException(String msg) {
        super(msg);
    }

    /**
     * Default message.
     */
    public JwtStoreServiceException() {
        super("Exception occured while trying to access or save repository data.");
    }
    
}
