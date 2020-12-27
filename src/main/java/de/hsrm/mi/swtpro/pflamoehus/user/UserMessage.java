package de.hsrm.mi.swtpro.pflamoehus.user;

/*
 * UserMessage for sending a message to frontend
 * 
 * @author Sarah Wenzel
 * @version 1
 */
public class UserMessage {

    private String email;
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
    
}
