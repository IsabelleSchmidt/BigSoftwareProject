package de.hsrm.mi.swtpro.pflamoehus.user;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Version;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import de.hsrm.mi.swtpro.pflamoehus.roles.Roles;
import de.hsrm.mi.swtpro.pflamoehus.adress.Adress;
import de.hsrm.mi.swtpro.pflamoehus.paymentmethods.Bankcard;
import de.hsrm.mi.swtpro.pflamoehus.paymentmethods.Creditcard;
import de.hsrm.mi.swtpro.pflamoehus.validation.user_db.ValidBirthDay;
import de.hsrm.mi.swtpro.pflamoehus.validation.user_db.ValidEmail;
import de.hsrm.mi.swtpro.pflamoehus.validation.user_db.ValidGender;
import de.hsrm.mi.swtpro.pflamoehus.validation.user_db.ValidPassword;

/*
 * User-Entity for its database.
 * 
 * @author Ann-Cathrin Fabian
 * @version 3
 */
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private long userID;

    @Version
    @JsonIgnore
    private long version;

    @NotEmpty(message = "EMPTY:Der Vorname muss angegeben werden.")
    @Size(min = 3, message="NOTVALID: Der Vorname muss mindestens 3 Buchstaben lang sein.")
    @Column(name = "firstname")
    private String firstName;

    @NotEmpty(message = "EMPTY:Der Nachname muss angegeben werden.")
    @Size(min = 2, message="NOTVALID: Der Nachname muss mindestens 2 Buchstaben lang sein.")
    @Column(name = "lastname")
    private String lastName;

    @NotEmpty(message = "EMPTY:Die Email-Adresse muss angegeben werden.")
    @Column(name = "EMAIL", unique = true)
    @ValidEmail
    private String email;

    @ValidBirthDay
    private LocalDate birthdate;

    @NotEmpty(message = "EMPTY:Es muss ein Passwort angegeben werden.")
    @ValidPassword
    @JsonProperty(access = Access.WRITE_ONLY)
    private String password;

    @Valid
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JoinTable(name = "User_Adresses", joinColumns = @JoinColumn(name = "userID"), inverseJoinColumns = @JoinColumn(name = "adressID"))
    private List<Adress> allAdresses;

    @NotEmpty(message = "EMPTY:Das Geschlecht muss angegeben werden.")
    @ValidGender
    private String gender;

    @Valid
    @ManyToMany(mappedBy = "user", cascade = CascadeType.DETACH)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Bankcard> bankcard;

    @Valid
    @ManyToMany(mappedBy = "user", cascade = CascadeType.DETACH)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Creditcard> creditcard;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name="user_roles", joinColumns = @JoinColumn(name="user_id"), inverseJoinColumns = @JoinColumn(name="role_id"))
    private List<Roles> roles = new ArrayList<>();

    public List<Roles> getRoles() {
        return this.roles;
    }

    public void setRoles(List<Roles> roles) {
        this.roles = roles;
    }

    /**
     * Get creditcards.
     * 
     * @return list of creditscards
     */

    public List<Creditcard> getCreditcard() {
        return this.creditcard;
    }

    /**
     * Set creditcards.
     * 
     * @param creditcard creditcards that have to be set
     */
    public void setCreditcard(List<Creditcard> creditcard) {
        this.creditcard = creditcard;
    }

    /**
     * Adds a new creditcard to the list of a user.
     * 
     * @param newCreditcard creditcard that should be added
     */
    public void addCreditcard(Creditcard newCreditcard) {
        if (!creditcard.contains(newCreditcard)) {
            creditcard.add(newCreditcard);
        }

    }

    /**
     * Removes a given creditcard from the list of a user.
     * 
     * @param deleteCard card that should get removed
     */
    public void removeCreditCard(Creditcard deleteCard) {
        if (deleteCard != null) {
            creditcard.remove(deleteCard);
        }

    }

    /**
     * Get bankcards.
     * 
     * @return list of bankcards
     */

    public List<Bankcard> getBankcard() {
        return this.bankcard;
    }

    /**
     * Set bankcards.
     * 
     * @param bankcard bankcards that have to be set
     */
    public void setBankcard(List<Bankcard> bankcard) {
        this.bankcard = bankcard;
    }

    /**
     * Adds a new bankcard to the list of a user.
     * 
     * @param newBankcard bankcard that sould be added
     */
    public void addBankcard(Bankcard newBankcard) {
        if (!bankcard.contains(newBankcard)) {
            bankcard.add(newBankcard);
        }
    }

    /**
     * Removes a given bankcard from the list of a user.
     * 
     * @param deleteBankcard bankcard that should get deleted
     */
    public void removeBankcard(Bankcard deleteBankcard) {
        if (deleteBankcard != null) {
            bankcard.remove(deleteBankcard);
        }

    }

    /**
     * Get gender.
     * 
     * @return gender
     */
    public String getGender() {
        return this.gender;
    }

    /**
     * Set gender.
     * 
     * @param gender gender that has to be set
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * Get adresses.
     * 
     * @return list of adresses
     */
    public List<Adress> getAdress() {
        return this.allAdresses;
    }

    /**
     * Set adresses.
     * 
     * @param allAdresses list of adresses that has to be set
     */
    public void setAdress(List<Adress> allAdresses) {
        this.allAdresses = allAdresses;
    }

    /**
     * Add a new adress to the list of all adresses owned by one user.
     * 
     * @param adress adress that has to be added to the adress list
     */
    public void addAdress(Adress adress) {
        if (!allAdresses.contains(adress)) {
            allAdresses.add(adress);
        }
    }

    /**
     * Removes a adress from the list of all adresses owned by one user.
     * 
     * @param adress adress that has to be removed from the adress list
     */
    public void removeAdress(Adress adress) {
        if (adress != null) {
            allAdresses.remove(adress);
        }

    }

    /**
     * Get birthdate.
     * 
     * @return birthdate
     */
    public LocalDate getBirthdate() {
        return this.birthdate;
    }

    /**
     * Set birthdate.
     * 
     * @param birthdate birthdate that has to be set
     */
    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    /**
     * Get last name.
     * 
     * @return lastname
     */
    public String getLastName() {
        return this.lastName;
    }

    /**
     * Set last name.
     * 
     * @param lastName lastname that has to be set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Get first name.
     * 
     * @return first name.
     */
    public String getFirstName() {
        return this.firstName;
    }

    /**
     * Set first name.
     * 
     * @param firstName firstname that has to be set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Get version.
     * 
     * @return version.
     */
    public long getVersion() {
        return this.version;
    }

    /**
     * Get id.
     * 
     * @return id
     */
    public long getId() {
        return this.userID;
    }

    /**
     * Get password.
     * 
     * @return password
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Set password.
     * 
     * @param password password that has to be set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Get email.
     * 
     * @return email
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * Set email.
     * 
     * @param email email that has to be set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * To create a user as a string.
     * 
     * @return string
     */
    @Override
    public String toString() {
        return "User {bankcard:" + bankcard + ", birthdate:" + birthdate + ", creditcard=" + creditcard + ", email:"
                + email + ", firstName:" + firstName + ", gender:" + gender + ", id:" + userID + ", lastName:"
                + lastName + ", passwort:" + password + ", version:" + version + ", roles:" + roles.toString() + "}";
    }

}
