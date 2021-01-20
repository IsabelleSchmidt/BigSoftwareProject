package de.hsrm.mi.swtpro.pflamoehus.payload.response;

/*
 * Shows, how a MessageResponse has to look like.
 * 
 * @author Ann-Cathrin Fabian
 * @version 2
 */
public class MessageResponse {

    private String message;

    private String field;

    
    /** 
     * @return String
     */
    public String getField() {
        return this.field;
    }

    
    /** 
     * Set field.
     * 
     * @param field to be set
     */
    public void setField(String field) {
        this.field = field;
    }

	
    /** 
     * Get message.
     * 
     * @return String
     */
    public String getMessage() {
		return message;
	}

	
    /** 
     * Set message.
     * 
     * @param message that has to be set.
     */
    public void setMessage(String message) {
		this.message = message;
    }

    @Override
    public String toString() {
        return "MessageResponse [field=" + field + ", message=" + message + "]";
    }
    
}
