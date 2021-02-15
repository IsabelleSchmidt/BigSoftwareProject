package de.hsrm.mi.swtpro.pflamoehus.payload.request;

import javax.validation.constraints.NotBlank;

/*
 * Shows, how a LoginRequest has to look like.
 * 
 * @author Ann-Cathrin Fabian
 * @version 1
 */
public class LoginRequest {
    
    @NotBlank(message="Die Email-Adresse muss angegeben werden.")
	private String email;

	@NotBlank(message="Das Passwort muss angegeben werden.")
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
