package de.hsrm.mi.swtpro.pflamoehus.email;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Version;

import de.hsrm.mi.swtpro.pflamoehus.validation.user_db.ValidEmail;

/*
 * Shows, how a PasswordRequest has to look like.
 * 
 * @author Sarah Wenzel
 * @version 1
 */
@Entity
public class PasswordRequest {

    @Id
    private long id;
    @Version
    private long version;
    
    @ValidEmail
    String email;

    String code;

    /*
    * Constructor to create a PasswordRequest.
    *
    */
    public PasswordRequest(String email, String code) {
        this.email = email;
        this.code = code;
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

    
    @Override
    public String toString() {
        return "PasswordRequest [code=" + code + ", email=" + email + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((email == null) ? 0 : email.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PasswordRequest other = (PasswordRequest) obj;
        if (email == null) {
            if (other.email != null)
                return false;
        } else if (!email.equals(other.email))
            return false;
        return true;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }
    
}
