package de.hsrm.mi.swtpro.pflamoehus.user;

import java.time.LocalDate;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Version;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import de.hsrm.mi.swtpro.pflamoehus.user.adress.Adress;
import de.hsrm.mi.swtpro.pflamoehus.user.paymentmethods.Bankcard;
import de.hsrm.mi.swtpro.pflamoehus.user.paymentmethods.Creditcard;
import de.hsrm.mi.swtpro.pflamoehus.validation.user.ValidBirthDay;
import de.hsrm.mi.swtpro.pflamoehus.validation.user.ValidEmail;
import de.hsrm.mi.swtpro.pflamoehus.validation.user.ValidGender;
import de.hsrm.mi.swtpro.pflamoehus.validation.user.ValidPassword;
@Entity
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private long userID;

    @Version
    @JsonIgnore
    private long version;
    
    @NotEmpty
    @Column(name="EMAIL", unique=true)
    @ValidEmail
    private String email;
    
    @ValidPassword
    @NotEmpty
    @JsonProperty(access =  Access.WRITE_ONLY)
    private String passwort;

    @NotEmpty @Size(min=3)
    @Column(name="firstname")
    private String firstName;

    @NotEmpty @Size(min=2)
    @Column(name="lastname")
    private String lastName;

    @ValidBirthDay
    private LocalDate birthdate;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="User_Adresses", joinColumns = @JoinColumn(name="userID"), inverseJoinColumns = @JoinColumn(name="adressID"))
    private List<Adress> allAdresses;

    @NotEmpty
    @ValidGender
    private String gender;

    @Valid
    @ManyToMany(mappedBy = "user")
    private List<Bankcard> bankcard;

    @Valid
    @ManyToMany(mappedBy = "user")
    private List<Creditcard> creditcard;

    
    public List<Creditcard> getCreditcard() {
        return this.creditcard;
    }

    public void setCreditcard(List<Creditcard> creditcard) {
        this.creditcard = creditcard;
    }

    public List<Bankcard> getBankcard() {
        return this.bankcard;
    }

    public void setBankcard(List<Bankcard> bankcard) {
        this.bankcard = bankcard;
    }
    
    /** 
     * @param adress
     */
    public void addAdress(Adress adress){
        if(!allAdresses.contains(adress)){
            allAdresses.add(adress);
        }
    }

    
    /** 
     * @return String
     */
    public String getGender() {
        return this.gender;
    }

    
    /** 
     * @param gender
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    
    /** 
     * @return List<Adress>
     */
    public List<Adress> getAdress() {
        return this.allAdresses;
    }

    
    /** 
     * @param allAdresses
     */
    public void setAdress(List<Adress> allAdresses) {
        this.allAdresses = allAdresses;
    }

    
    /** 
     * @return LocalDate
     */
    public LocalDate getBirthdate() {
        return this.birthdate;
    }

    
    /** 
     * @param birthdate
     */
    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    
    /** 
     * @return String
     */
    public String getLastName() {
        return this.lastName;
    }

    
    /** 
     * @param lastName
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    
    /** 
     * @return String
     */
    public String getFirstName() {
        return this.firstName;
    }

    
    /** 
     * @param firstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    
    /** 
     * @return long
     */
    public long getVersion() {
        return this.version;
    }


    
    /** 
     * @return long
     */
    public long getId() {
        return this.userID;
    }

    
    /** 
     * @return String
     */
    public String getPassword() {
        return this.passwort;
    }

    
    /** 
     * @param password
     */
    public void setPassowrd(String password) {
        this.passwort = password;
    }

    
    /** 
     * @return String
     */
    public String getEmail() {
        return this.email;
    }

    
    /** 
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User [bankcard=" + bankcard + ", birthdate=" + birthdate + ", creditcard="
                + creditcard + ", email=" + email + ", firstName=" + firstName + ", gender=" + gender + ", id=" + userID
                + ", lastName=" + lastName + ", passwort=" + passwort + ", version=" + version + "]";
    }


}
