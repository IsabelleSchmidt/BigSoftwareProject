package de.hsrm.mi.swtpro.pflamoehus.exceptions;

public class UserServiceException extends RuntimeException{

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    public UserServiceException(String msg){
        super(msg);
    }

    public UserServiceException(){
        super("Exception occured while trying to access or save repository data.");
    }
}
