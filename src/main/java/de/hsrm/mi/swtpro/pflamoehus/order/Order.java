package de.hsrm.mi.swtpro.pflamoehus.order;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Version;
import javax.validation.constraints.FutureOrPresent;

import com.fasterxml.jackson.annotation.JsonIgnore;

import de.hsrm.mi.swtpro.pflamoehus.user.User;

/*
 * Order-Entitiy for its database.
 * 
 * @author Ann-Cathrin Fabian
 * @version 1
 */
@Entity
public class Order {    

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long orderNR;

    @Version
    @JsonIgnore
    private long version;

    @ManyToOne
    private User userID;

    @FutureOrPresent
    private LocalDate deliveryDate;

    private Status status;

    /** 
     * Get orderNr.
     * 
     * @return long
     */
    public long getOrderNR() {
        return orderNR;
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
     * Set version
     * 
     * @param version version that should be set.
     */
    public void setVersion(long version) {
        this.version = version;
    }

    
    /** 
     * Get user.
     * 
     * @return User
     */
    public User getUserID() {
        return userID;
    }

    
    /** 
     * Set user.
     * 
     * @param userID user that should be set.
     */
    public void setUserID(User userID) {
        this.userID = userID;
    }

    
    /** 
     * Get delivery date.
     * 
     * @return LocalDate
     */
    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    
    /** 
     * Set delivery date.
     * 
     * @param deliveryDate new date
     */
    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    
    /** 
     * Get status.
     * 
     * @return Status
     */
    public Status getStatus() {
        return status;
    }

    
    /** 
     * Set status.
     * 
     * @param status new status
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    
    /** 
     * Order to string.
     * 
     * @return String
     */
    @Override
    public String toString() {
        return "Order [deliveryDate=" + deliveryDate + ", orderNR=" + orderNR + ", status=" + status + ", userID="
                + userID + ", version=" + version + "]";
    }

    


}
