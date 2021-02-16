package de.hsrm.mi.swtpro.pflamoehus.user.paymentmethods;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.JoinColumn;
import javax.persistence.Version;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.validation.annotation.Validated;
import de.hsrm.mi.swtpro.pflamoehus.user.User;
import de.hsrm.mi.swtpro.pflamoehus.validation.user_db.ValidCreditCardNumber;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/*
 * Creditcard entity for its database.
 * 
 * @author Ann-Cathrin Fabian
 * @version 1
 */
@Entity
@Validated
public class Creditcard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private long id;

    @Version
    @JsonIgnore
    private long version;

    @NotEmpty(message="Der Besitzer der Karte muss angebeben werden.")
    @Size(min = 3, message="Der angegebene Besitzer ist ungültig.")
    private String cowner;

    @NotEmpty(message="Die Kreditkartennummer muss angegeben werden.")
    @ValidCreditCardNumber
    private String creditcardnumber;

    @NotNull(message="Das Ablaufdatum der Karte muss angebeben werden.")
    @Future
    private LocalDate dateOfExpiry;

    @ManyToMany(fetch = FetchType.EAGER)
    @JsonIgnore
    @JoinTable(name = "User_Creditcards", joinColumns = @JoinColumn(name = "id"), inverseJoinColumns = @JoinColumn(name = "userID"))
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
     * Get Owner.
     * 
     * @return owner
     */
    public String getCowner() {
        return this.cowner;
    }

    /**
     * Set owner.
     * 
     * @param owner owner that has to be set
     */
    public void setCowner(String owner) {
        this.cowner = owner;
    }

    /**
     * Get creditcardnumber.
     * 
     * @return creditcardnumber
     */
    public String getCreditcardnumber() {
        return this.creditcardnumber;
    }

    /**
     * Set creditcardnumber.
     * 
     * @param number creditcard number that has to be set
     */
    public void setCreditcardnumber(String number) {
        this.creditcardnumber = number;
    }

    /**
     * Get date of experiy.
     * 
     * @return date of experiy.
     */
    public LocalDate getDateOfExpiry() {
        return this.dateOfExpiry;
    }

    /**
     * Set date of experiy.
     * 
     * @param dateOfExperiy date of experiy that has ot be set
     */
    public void setDateOfExpiry(LocalDate dateOfExperiy) {
        this.dateOfExpiry = dateOfExperiy;
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
     * @param user list of users that has to be set
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

    @Override
    public String toString() {
        return "Creditcard [creditcardnumber=" + creditcardnumber + ", dateOfExpiry=" + dateOfExpiry + ", id=" + id
                + ", owner=" + cowner + ", version=" + version + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((creditcardnumber == null) ? 0 : creditcardnumber.hashCode());
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
        Creditcard other = (Creditcard) obj;
        if (creditcardnumber == null) {
            if (other.creditcardnumber != null)
                return false;
        } else if (!creditcardnumber.equals(other.creditcardnumber))
            return false;
        if (user == null) {
            if (other.user != null)
                return false;
        } else if (!user.equals(other.user))
            return false;
        return true;
    }





}
