package de.hsrm.mi.swtpro.pflamoehus.paymentmethods;

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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import de.hsrm.mi.swtpro.pflamoehus.user.User;
import de.hsrm.mi.swtpro.pflamoehus.validation.user_db.ValidCreditCardNumber;
import java.time.LocalDate;
import java.util.List;

/*
 * Creditcard entity for its database.
 * 
 * @author Ann-Cathrin Fabian
 * @version 1
 */
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

    @NotEmpty(message="Die Kreditkartennummer muss angegeben werden.")
    @JsonProperty(access = Access.WRITE_ONLY)
    @ValidCreditCardNumber
    private String creditcardnumber;

    @NotNull(message="Das Ablaufdatum der Karte muss angebeben werden.")
    @Future
    private LocalDate dateOfExpiry;

    @ManyToMany(fetch = FetchType.EAGER)
    @JsonIgnore
    @JoinTable(name = "User_Creditcards", joinColumns = @JoinColumn(name = "id"), inverseJoinColumns = @JoinColumn(name = "userID"))
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
     * Get Owner.
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
    public List<User> getUser() {
        return user;
    }

    /**
     * Set users.
     * 
     * @param user list of users that has to be set
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

    @Override
    public String toString() {
        return "Creditcard [creditcardnumber=" + creditcardnumber + ", dateOfExpiry=" + dateOfExpiry + ", id=" + id
                + ", owner=" + owner + ", version=" + version + "]";
    }
    

}
