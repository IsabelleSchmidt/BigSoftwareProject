package de.hsrm.mi.swtpro.pflamoehus.exceptions;

public class ProductServiceException extends RuntimeException{

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    public ProductServiceException(String msg){
        super(msg);
    }

    public ProductServiceException(){
        super("Exception occured while trying to access or save repository data.");
    }
}