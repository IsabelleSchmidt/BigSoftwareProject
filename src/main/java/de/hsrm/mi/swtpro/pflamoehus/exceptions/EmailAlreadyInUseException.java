package de.hsrm.mi.swtpro.pflamoehus.exceptions;

public class EmailAlreadyInUseException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public EmailAlreadyInUseException(){
        System.out.println("Email is already in use.");
    }

    EmailAlreadyInUseException(String error){
        System.out.println(error);
    }
    
}

