package de.hsrm.mi.swtpro.pflamoehus.user;

/*
 * UserMessage for sending a message to frontend
 * 
 * @author Sarah Wenzel, Ann-Cathrin Fabian
 * @version 2
 */
public class UserMessage {

    private String email;
    private String type;
    private String message;

    /**
     * Get email.
     * 
     * @return email
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * Set email.
     * 
     * @param email email that has to be set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Get message.
     * 
     * @return message
     */
    public String getMessage() {
        return this.message;
    }

    /**
     * Set message.
     * 
     * @param message message that has to be set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    
    /** 
     * Set type.
     * 
     * @return String
     */
    public String getType() {
        return this.type;
    }

    
    /** 
     * Get type.
     * 
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    
}
