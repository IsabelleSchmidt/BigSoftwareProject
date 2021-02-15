package de.hsrm.mi.swtpro.pflamoehus.user;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Version;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import org.springframework.validation.annotation.Validated;
import de.hsrm.mi.swtpro.pflamoehus.user.roles.Roles;
import de.hsrm.mi.swtpro.pflamoehus.user.adress.Adress;
import de.hsrm.mi.swtpro.pflamoehus.order.Order;
import de.hsrm.mi.swtpro.pflamoehus.user.paymentmethods.Bankcard;
import de.hsrm.mi.swtpro.pflamoehus.user.paymentmethods.Creditcard;
import de.hsrm.mi.swtpro.pflamoehus.validation.user_db.ValidBirthDay;
import de.hsrm.mi.swtpro.pflamoehus.validation.user_db.ValidEmail;

/*
 * User-Entity for its database.
 * 
 * @author Ann-Cathrin Fabian, Svenja Schenk
 * @version 4
 */
@Entity
@Validated
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userID;

    @Version
    @JsonIgnore
    private long version;

    @NotEmpty(message = "Der Vorname muss angegeben werden.")
    @Size(min = 3, message = "Der Vorname muss mindestens 3 Buchstaben lang sein.")
    @Column(name = "firstname")
    private String firstName;

    @NotEmpty(message = "Der Nachname muss angegeben werden.")
    @Size(min = 2, message = "Der Nachname muss mindestens 2 Buchstaben lang sein.")
    @Column(name = "lastname")
    private String lastName;

    @NotEmpty(message = "Die Email-Adresse muss angegeben werden.")
    @Column(name = "EMAIL", unique = true)
    @ValidEmail
    private String email;

    @ValidBirthDay
    private LocalDate birthdate;

    @NotEmpty(message = "Es muss ein Passwort angegeben werden.")
    @JsonProperty(access = Access.WRITE_ONLY)
    @JsonIgnore
    private String password;

    @ManyToMany(cascade = CascadeType.DETACH)
    @JoinTable(name = "User_Adresses", joinColumns = @JoinColumn(name = "userID"), inverseJoinColumns = @JoinColumn(name = "adressID"))
    private Set<Adress> allAdresses = new HashSet<>();

    @Enumerated(EnumType.STRING)
    private Gender gender = Gender.DIVERSE;

    @ManyToMany(mappedBy = "user", cascade = CascadeType.DETACH)
    private Set<Bankcard> bankcard = new HashSet<>();

    @ManyToMany(mappedBy = "user", cascade = CascadeType.DETACH)
    private Set<Creditcard> creditcard = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Roles> roles = new HashSet<>();

    @OneToMany(cascade = CascadeType.DETACH, mappedBy = "user")
    @JsonIgnore
    private Set<Order> allOrders = new HashSet<>();

    /**
     * Get roles.
     * 
     * @return roles
     */
    public Set<Roles> getRoles() {
        return this.roles;
    }

    /**
     * Set roles.
     * 
     * @param roles roles that should be set.
     */
    public void setRoles(Set<Roles> roles) {
        this.roles = roles;
    }

    /**
     * Add role.
     * 
     * @param role role that should be added
     */
    public void addRole(Roles role) {
        if (!roles.contains(role)) {
            roles.add(role);
        }
    }

    /**
     * Remove role.
     * 
     * @param role that should get removed
     */
    public void removeRole(Roles role) {
        if (role != null) {
            roles.remove(role);
        }
    }

    /**
     * Get creditcards.
     * 
     * @return list of creditscards
     */

    public Set<Creditcard> getCreditcard() {
        return this.creditcard;
    }

    /**
     * Set creditcards.
     * 
     * @param creditcard creditcards that have to be set
     */
    public void setCreditcard(Set<Creditcard> creditcard) {
        this.creditcard = creditcard;
    }




    /**
     * Get bankcards.
     * 
     * @return list of bankcards
     */

    public Set<Bankcard> getBankcard() {
        return this.bankcard;
    }

    /**
     * Set bankcards.
     * 
     * @param bankcard bankcards that have to be set
     * 
     */
    public void setBankcard(Set<Bankcard> bankcard) {
        this.bankcard = bankcard;
    }



    /**
     * Get gender.
     * 
     * @return gender
     */
    public Gender getGender() {
        return this.gender;
    }

    /**
     * Set gender.
     * 
     * @param gender gender that has to be set
     */
    public void setGender(Gender gender) {
        this.gender = gender;
    }

    /**
     * Get adresses.
     * 
     * @return list of adresses
     */
    public Set<Adress> getAllAdresses() {
        return this.allAdresses;
    }

    /**
     * Set adresses.
     * 
     * @param allAdresses list of adresses that has to be set
     */
    public void setAllAdresses(Set<Adress> allAdresses) {
        this.allAdresses = allAdresses;
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
     * User to string.
     * 
     * @return String
     */
    @Override
    public String toString() {
        return "User [allAdresses=" + allAdresses + ", bankcard=" + bankcard + ", birthdate=" + birthdate
                + ", creditcard=" + creditcard + ", email=" + email + ", firstName=" + firstName + ", gender=" + gender
                + ", lastName=" + lastName + ", orders=" + ", password=" + password + ", roles=" + roles
                + ", userID=" + userID + ", version=" + version + "]";
    }

    
    /** 
     * Get orders.
     * 
     * @return all orders
     */
    public Set<Order> getAllOrders() {
        return allOrders;
    }

    
    /** 
     * Set orders. 
     * 
     * @param allOrders to be set
     */
    public void setAllOrders(Set<Order> allOrders) {
        this.allOrders = allOrders;
    }


}
