package de.hsrm.mi.swtpro.pflamoehus.payload.response;

import java.util.ArrayList;
import java.util.List;

import de.hsrm.mi.swtpro.pflamoehus.product.productapi.Errormessage;

/**
 * Picture Response Entity for coummunication between backend and frontend
 * 
 * @author Marie Scharhag
 * @version 1
 */
public class PictureResponse {
    private List<Errormessage> allErrors;

    /**
     *  Constructor for Picture Response
     */
    public PictureResponse(){
        allErrors = new ArrayList<>();
    }

    /**
     * Constructor for Picture Response
     * 
     * @param allErrors Arrays that occured while saving Picture
     */
    public PictureResponse(List<Errormessage> allErrors){
        this.allErrors = allErrors;
    }

    /**
     * dds new Error Message to PictureResponse
     * 
     * @param error Error that should add to the Picture Response
     */
    public void addErrormessage(Errormessage error){
        allErrors.add(error);
    }

    /**
     * 
     * @return returns List of all Errormessages
     */
    public List<Errormessage> getAllErrors(){
        return allErrors;
    }

    /**
     * Adds a List of Errormessages
     * 
     * @param allErrors List of Errormessages that should be added
     */
    public void setAllErrors(List<Errormessage> allErrors){
        this.allErrors = allErrors;
    }
    
}
