package de.hsrm.mi.swtpro.pflamoehus.exceptions;

public class EmailAlreadyInUse extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public EmailAlreadyInUse(){
        System.out.println("Email is already in use.");
    }

    EmailAlreadyInUse(String error){
        System.out.println(error);
    }
    
}

