package de.hsrm.mi.swtpro.pflamoehus.authentication.payload.response;

import java.util.List;

/*
 * Shows, how a JwtResponse has to look like.
 * 
 * @author Ann-Cathrin Fabian
 * @version 1
 */
public class JwtResponse {

    private String token;
	private String email;
	private List<String> roles;

	public JwtResponse(String accessToken, String email, List<String> roles) {
		this.token = accessToken;
		this.email = email;
		this.roles = roles;
	}

	
	/** 
	 * Get accessToken.
	 * 
	 * @return String
	 */
	public String getAccessToken() {
		return token;
	}

	
	/** 
	 * Set accesstoken.
	 * 
	 * @param accessToken that has to be set.
	 */
	public void setAccessToken(String accessToken) {
		this.token = accessToken;
	}

	
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
	 * @param email that has to be set.
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	
	/** 
	 * Get Roles.
	 * 
	 * @return List
	 */
	public List<String> getRoles() {
		return roles;
	}

	@Override
	public String toString() {
		return "JwtResponse [email=" + email + ", roles=" + roles + ", token=" + token + "]";
	}

	public String toJson(){
		return "{ email : " + email + ", roles : " + roles + ", token : " + token + "}";
	}
    
}
