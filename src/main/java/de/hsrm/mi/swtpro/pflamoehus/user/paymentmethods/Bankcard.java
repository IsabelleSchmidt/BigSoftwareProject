package de.hsrm.mi.swtpro.pflamoehus.user.paymentmethods;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Version;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import de.hsrm.mi.swtpro.pflamoehus.user.User;

@Entity
public class Bankcard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private long id;

    @Version
    @JsonIgnore
    private long version;

    @NotEmpty
    @Pattern(regexp="((DE)\\d{2}( \\d{4}){4} \\d{2}")
    private String iban;

    @NotEmpty
    @Size(min=6)
    private String owner;

    @NotEmpty
    @Size(min=3)
    private String bank;

    @ManyToOne
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
    public String getIban() {
        return this.iban;
    }

    
    /** 
     * @param iban
     */
    public void setIban(String iban) {
        this.iban = iban;
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
    public String getBank() {
        return this.bank;
    }

    
    /** 
     * @param bank
     */
    public void setBank(String bank) {
        this.bank = bank;
    }
    
}
