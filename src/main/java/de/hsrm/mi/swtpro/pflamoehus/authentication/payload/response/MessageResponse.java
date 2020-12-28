package de.hsrm.mi.swtpro.pflamoehus.authentication.payload.response;

/*
 * Shows, how a MessageResponse has to look like.
 * 
 * @author Ann-Cathrin Fabian
 * @version 1
 */
public class MessageResponse {

    private String type;
    private String message;

	
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
    
    
    /** 
     * Get type.
     * 
     * @return String
     */
    public String getType() {
        return this.type;
    }

    
    /** 
     * Set type.
     * 
     * @param type that has to be set.
     */
    public void setType(String type) {
        this.type = type;
    }
}
