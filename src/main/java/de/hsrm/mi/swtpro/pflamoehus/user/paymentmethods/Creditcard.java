package de.hsrm.mi.swtpro.pflamoehus.user.paymentmethods;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Version;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import de.hsrm.mi.swtpro.pflamoehus.user.User;

import java.time.LocalDate;

@Entity
public class Creditcard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private long id;

    @Version
    @JsonIgnore
    private long version;

    @NotEmpty
    private String owner;

    @NotEmpty
    @JsonProperty(access =  Access.WRITE_ONLY)
    //TODO: Validator fuer Creditcard
    private String creditcardnumber;

    @NotEmpty
    private LocalDate dateOfExpiry;

    @ManyToMany(fetch = FetchType.LAZY)
    private User user;

    
    /** 
     * @return long
     */
    public long getId() {
        return this.id;
    }


    
    /** 
     * @return long
     */
    public long getVersion() {
        return this.version;
    }


    
    /** 
     * @return String
     */
    public String getOwner() {
        return this.owner;
    }

    
    /** 
     * @param owner
     */
    public void setOwner(String owner) {
        this.owner = owner;
    }

    
    /** 
     * @return String
     */
    public String getCreditcardnumber() {
        return this.creditcardnumber;
    }

    
    /** 
     * @return LocalDate
     */
    public LocalDate getDateOfExperiy() {
        return this.dateOfExpiry;
    }

    
    /** 
     * @param dateOfExperiy
     */
    public void setDateOfExperiy(LocalDate dateOfExperiy) {
        this.dateOfExpiry = dateOfExperiy;
    }
    
}
