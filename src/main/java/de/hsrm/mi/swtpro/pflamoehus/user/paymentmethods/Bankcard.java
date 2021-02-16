package de.hsrm.mi.swtpro.pflamoehus.user.paymentmethods;

import java.util.HashSet;
import java.util.Set;
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
import org.springframework.validation.annotation.Validated;
import de.hsrm.mi.swtpro.pflamoehus.user.User;

/*
 * Bankcard entity for its database.
 * 
 * @author Ann-Cathrin Fabian
 * @version 1
 */
@Entity
@Validated
public class Bankcard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private long id;

    @Version
    @JsonIgnore
    private long version;

    @NotEmpty(message="Die IBAN muss angebeben werden.")
    @Pattern(regexp = "DE\\d{2}[ ]\\d{4}[ ]\\d{4}[ ]\\d{4}[ ]\\d{4}[ ]\\d{2}|DE\\d{20}$", message="Die IBAN ist nicht gültig.")
    private String iban;

    @NotEmpty(message="Der Besitzer der Karte muss angebeben werden.")
    @Size(min = 3, message="Der angegebene Besitzer ist ungültig.")
    private String owner;

    @NotEmpty(message="Die Bank muss angegeben werden")
    @Size(min = 3, message="Die angegebene Bank ist ungültig.")
    private String bank;

    @ManyToMany(fetch = FetchType.EAGER)
    @JsonIgnore
    @JoinTable(name = "User_Bankcards", joinColumns = @JoinColumn(name = "id"), inverseJoinColumns = @JoinColumn(name = "userID"))
    private Set<User> user = new HashSet<>();

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
    public Set<User> getUser() {
        return user;
    }

    /**
     * Set users.
     * 
     * @param user users that have to be set
     */
    public void setUser(Set<User> user) {
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
        return "Bankcard [bank=" + bank + ", iban=" + iban + ", id=" + id + ", owner=" + owner
                + ", version=" + version + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((iban == null) ? 0 : iban.hashCode());
        result = prime * result + ((user == null) ? 0 : user.hashCode());
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
        Bankcard other = (Bankcard) obj;
        if (iban == null) {
            if (other.iban != null)
                return false;
        } else if (!iban.equals(other.iban))
            return false;
        if (user == null) {
            if (other.user != null)
                return false;
        } else if (!user.equals(other.user))
            return false;
        return true;
    }

  


}
