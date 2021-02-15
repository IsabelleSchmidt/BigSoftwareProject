package de.hsrm.mi.swtpro.pflamoehus.email;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
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
    @GeneratedValue
    private long id;

    @Version
    private long version;
    
    @ValidEmail
    @Column(unique=true)
    String email;

    String code;

    Long timestamp;

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

    /** 
	 * Get Id.
	 * 
	 * @return long
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
	 * @return long
	 */
    public long getVersion() {
        return version;
    }

    /** 
	 * Set verson. 
	 * 
	 * @param version to be set
	 */
    public void setVersion(long version) {
        this.version = version;
    }

    /** 
	 * Get timestamp.
	 * 
	 * @return long
	 */
    public Long getTimestamp() {
        return timestamp;
    }

    /** 
	 * Set timestamp. 
	 * 
	 * @param timestamp to be set
	 */
    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "PasswordRequest [code=" + code + ", email=" + email + ", id=" + id + ", timestamp=" + timestamp
                + ", version=" + version + "]";
    }
    
}
