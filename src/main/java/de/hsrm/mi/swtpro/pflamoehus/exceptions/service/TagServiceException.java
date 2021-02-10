package de.hsrm.mi.swtpro.pflamoehus.exceptions.service;

public class TagServiceException extends RuntimeException{

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public TagServiceException(){
        super("Fehler beim bearbeiten der Tag Datenbank.");
    }

    public TagServiceException(String msg){
        super(msg);
    }
    
}
