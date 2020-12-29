package de.hsrm.mi.swtpro.pflamoehus.authentication.payload.request;

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

	public String getEmail() {
		return email;
	}

	public void setUsername(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
