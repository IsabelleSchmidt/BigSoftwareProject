package de.hsrm.mi.swtpro.pflamoehus.payload.request;

import javax.validation.constraints.NotEmpty;
import de.hsrm.mi.swtpro.pflamoehus.validation.user_db.ValidPassword;

/*
 * Shows, how a NewPasswordRequest has to look like.
 * 
 * @author Sarah Wenzel
 * @version 1
 */
public class NewPasswordRequest {

	private String email;

    @NotEmpty(message = "Es muss ein Passwort angegeben werden.")
    @ValidPassword
    private String password;
    
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
	 * Get password.
	 * 
	 * @return String
	 */
	public String getPassword() {
		return password;
	}

	
	/** 
	 * Set password.
	 * 
	 * @param password to be set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
    
}
