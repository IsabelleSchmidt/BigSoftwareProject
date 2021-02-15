package de.hsrm.mi.swtpro.pflamoehus.product.productapi;

/**
 * ErrorMessage that Returns to frontend
 * 
 * @author Svenja Schenk, Marie Scharhag
 */
public class Errormessage {
    private String field;
    private String message;

    /**
     * Constructor for Errormessage
     * 
     * @param field set error field
     * @param message set error message
     */
    public Errormessage(String field, String message){
        this.field = field;
        this.message = message;
    }

    /**
     * 
     * @return error field
     */
    public String getField() {
        return field;
    }

    /**
     * set Errorfield
     * @param field Field for Errorfield
     */
    public void setField(String field) {
        this.field = field;
    }

    /**
     * 
     * @return Error Message
     */
    public String getMessage() {
        return message;
    }


    /**
     * Sets Errormessage
     * @param message Message to set Errormessage
     */
    public void setMessage(String message) {
        this.message = message;
    }
}
