package de.hsrm.mi.swtpro.pflamoehus.payload.request;

/*
 * Shows, how a EmailRequest has to look like.
 * EmailRequest to send email with a code to create the link to reset the password.
 * 
 * @author Sarah Wenzel
 * @version 1
 */
public class EmailRequest {

    private String email;

    private String code;

    /** 
	 * Get email.
	 * 
	 * @return String
	 */
    public String getEmail() {
        return email;
    }

    /** 
	 * Set email. 
	 * 
	 * @param email to be set
	 */
    public void setEmail(String email) {
        this.email = email;
    }

    /** 
	 * Get code.
	 * 
	 * @return String
	 */
    public String getCode() {
        return code;
    }

    /** 
	 * Set code. 
	 * 
	 * @param code to be set
	 */
    public void setCode(String code) {
        this.code = code;
    }

    
    
}
