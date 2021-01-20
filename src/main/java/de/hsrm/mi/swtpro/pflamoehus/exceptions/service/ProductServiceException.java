package de.hsrm.mi.swtpro.pflamoehus.exceptions.service;

/**
 * ProductServiceException for errors in the ProductSerivce classes.
 * 
 * @author Svenja Schenk
 * @version 1
 */
public class ProductServiceException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * Method for throwing a exception with a custom message.
     * 
     * @param msg message that gets printed
     */
    public ProductServiceException(String msg) {
        super(msg);
    }

    /**
     * Default massage.
     */
    public ProductServiceException() {
        super("Exception occured while trying to access or save repository data.");
    }
}