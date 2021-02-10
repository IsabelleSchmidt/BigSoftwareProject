package de.hsrm.mi.swtpro.pflamoehus.exceptions;

/**
 * PictureServiceException for errors in the PictureSerivce classes.
 * 
 * @author Marie Scharhag
 * @version 1
 */

public class PictureServiceException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    /**
     * Method for throwing a exception with a custom message.
     * 
     * @param msg -> message that gets printed
     */
    public PictureServiceException(String msg) {
        super(msg);
    }

    /**
     * Default massage.
     */
    public PictureServiceException() {
        super("Exception occured while trying to access or save repository data.");
    }
}
