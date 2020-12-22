package de.hsrm.mi.swtpro.pflamoehus.user.paymentmethods;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Version;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import de.hsrm.mi.swtpro.pflamoehus.user.User;

/*
 * Bankcard entity for its database.
 * 
 * @author Ann-Cathrin Fabian
 * @version 1
 */
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
    @Pattern(regexp = "DE\\d{2}[ ]\\d{4}[ ]\\d{4}[ ]\\d{4}[ ]\\d{4}[ ]\\d{2}|DE\\d{20}")
    @JsonProperty(access = Access.WRITE_ONLY)
    private String iban;

    @NotEmpty
    @Size(min = 6)
    private String owner;

    @NotEmpty
    @Size(min = 3)
    private String bank;

    @ManyToMany(fetch = FetchType.EAGER)
    @JsonIgnore
    @JoinTable(name = "User_Bankcards", joinColumns = @JoinColumn(name = "id"), inverseJoinColumns = @JoinColumn(name = "userID"))
    private List<User> user;

    /**
     * Get id.
     * 
     * @return id
     */
    public long getId() {
        return this.id;
    }

    /**
     * Get version.
     * 
     * @return version
     */
    public long getVersion() {
        return this.version;
    }

    /**
     * Set iban.
     * 
     * @param iban iban that has to be set
     */
    public void setIban(String iban) {
        this.iban = iban;
    }

    /**
     * Get iban.
     * 
     * @return iban
     */
    public String getIban() {
        return this.iban;
    }

    /**
     * Get owner.
     * 
     * @return owner
     */
    public String getOwner() {
        return this.owner;
    }

    /**
     * Set owner.
     * 
     * @param owner owner that has to be set
     */
    public void setOwner(String owner) {
        this.owner = owner;
    }

    /**
     * Get bank.
     * 
     * @return bank
     */
    public String getBank() {
        return this.bank;
    }

    /**
     * Set bank.
     * 
     * @param bank bank that has to be set
     */
    public void setBank(String bank) {
        this.bank = bank;
    }

    /**
     * Get users.
     * 
     * @return list of users
     */
    public List<User> getUser() {
        return user;
    }

    /**
     * Set users.
     * 
     * @param user users that have to be set
     */
    public void setUser(List<User> user) {
        this.user = user;
    }

    /**
     * Adds a user to the user list.
     * 
     * @param us user that should get added
     */
    public void addUser (User us){
        if(!user.contains(us)){
            user.add(us);
        }
    }

    /**
     * Removes a user from the user list.
     * 
     * @param us user that should get removed
     */
    public void removeUser(User us){
        if (user != null){
            user.remove(us);
        }
    }

    
     /**
     * To generate a bankcard as a string.
     */
    @Override
    public String toString() {
        return "Bankcard [bank=" + bank + ", iban=" + iban + ", id=" + id + ", owner=" + owner + ", user=" + user
                + ", version=" + version + "]";
    }

}