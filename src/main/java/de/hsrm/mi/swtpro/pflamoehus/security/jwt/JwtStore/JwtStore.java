package de.hsrm.mi.swtpro.pflamoehus.security.jwt.JwtStore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonIgnore;

/*
 * JwtStore-Entity for its database.
 * 
 * @author Ann-Cathrin Fabian
 * @version 1
 */
@Entity
public class JwtStore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Version
    @JsonIgnore
    long version;

    String token;

    
    /** 
     * Get id.
     * 
     * @return id
     */
    public long getId() {
        return id;
    }

    
    /** 
     * Set id.
     * 
     * @param id to be set
     */
    public void setId(long id) {
        this.id = id;
    }

    
    /** 
     * Get version.
     * 
     * @return version
     */
    public long getVersion() {
        return version;
    }

    
    /** 
     * Set version.
     * 
     * @param version to be set
     */
    public void setVersion(long version) {
        this.version = version;
    }

    
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

    
    /** 
     * Jwt store as a string.
     * 
     * @return String
     */
    @Override
    public String toString() {
        return "JwtStore [id=" + id + ", token=" + token + ", version=" + version + "]";
    }

   


    
    

    
}
