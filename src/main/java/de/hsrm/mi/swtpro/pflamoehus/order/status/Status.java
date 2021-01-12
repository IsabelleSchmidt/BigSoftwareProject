package de.hsrm.mi.swtpro.pflamoehus.order.status;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Version;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.validation.annotation.Validated;
import de.hsrm.mi.swtpro.pflamoehus.validation.order_db.ValidStatus;

/*
 * Status-Entity for its database.
 * 
 * @author Svenja Schenk
 * @version 1
 */
@Entity
@Validated
public class Status {

    @Id
    @GeneratedValue
    private long statusID;

    @Version
    @JsonIgnore
    private long version = 1;
 
    @ValidStatus
    @Column(unique = true)
    private String statuscode;

    /**
     * Get statusId.
     * 
     * @return long
     */
    public long getStatusID() {
        return statusID;
    }


    /**
     * Get statuscode.
     * 
     * @return String
     */
    public String getStatuscode() {
        return statuscode;
    }

    /**
     * Set statuscode.
     * 
     * @param statuscode to be set
     */
    public void setStatuscode(String statuscode) {
        this.statuscode = statuscode;
    }


    /**
     * Status to string.
     * 
     * @return String
     */
    @Override
    public String toString() {
        return "Status [ statusID=" + statusID
                + ", statuscode=" + statuscode + ", version=" + version + "]";
    }

    // TODO: in allen Entities checken ob wir ein Set oder eine Liste brauchen
}
