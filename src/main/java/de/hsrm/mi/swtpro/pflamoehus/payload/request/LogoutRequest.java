package de.hsrm.mi.swtpro.pflamoehus.payload.request;

public class LogoutRequest {

    String token;

    
    /** 
     * Get token.
     * 
     * @return token
     */
    public String getToken() {
        return token;
    }

    
    /** 
     * Set token.
     * 
     * @param token to be set
     */
    public void setToken(String token) {
        this.token = token;
    }

    
}
