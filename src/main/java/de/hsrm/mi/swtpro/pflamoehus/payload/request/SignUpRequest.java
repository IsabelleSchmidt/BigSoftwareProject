package de.hsrm.mi.swtpro.pflamoehus.payload.request;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import de.hsrm.mi.swtpro.pflamoehus.user.Gender;
import de.hsrm.mi.swtpro.pflamoehus.validation.user_db.ValidBirthDay;
import de.hsrm.mi.swtpro.pflamoehus.validation.user_db.ValidEmail;
import de.hsrm.mi.swtpro.pflamoehus.validation.user_db.ValidPassword;

/*
 * Shows, how a SignUpRequest has to look like.
 * 
 * @author Ann-Cathrin Fabian
 * @version 1
 */
public class SignUpRequest {

    @NotEmpty(message = "Der Vorname muss angegeben werden.")
    @Size(min = 3, message = "Der Vorname muss mindestens 3 Buchstaben lang sein.")
    private String firstName;

    @NotEmpty(message = "Der Nachname muss angegeben werden.")
    @Size(min = 2, message = "Der Nachname muss mindestens 2 Buchstaben lang sein.")
    private String lastName;

    @NotEmpty(message = "Die Email-Adresse muss angegeben werden.")
    @Column(name = "EMAIL", unique = true)
    @ValidEmail
    private String email;

    @ValidBirthDay
    private LocalDate birthdate;

    private Gender gender;

    @NotEmpty(message = "Es muss ein Passwort angegeben werden.")
    @ValidPassword
    private String password;

    private List<String> roles;

    /**
     * Get role.
     * 
     * @return String
     */
    public List<String> getRole() {
        return this.roles;
    }

    /**
     * Set role.
     * 
     * @param role that has to be set.
     */
    public void setRole(List<String> role) {
        this.roles = role;
    }

    /**
     * Get password.
     * 
     * @return String
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Set password.
     * 
     * @param password that has to be set.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Get gender.
     * 
     * @return String
     */
    public Gender getGender() {
        return this.gender;
    }

    /**
     * Set gender.
     * 
     * @param gender that has to be set.
     */
    public void setGender(Gender gender) {
        this.gender = gender;
    }

    /**
     * Get birthdate.
     * 
     * @return LocalDate
     */
    public LocalDate getBirthdate() {
        return this.birthdate;
    }

    /**
     * Set birthdate.
     * 
     * @param birthdate that has to be set.
     */
    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    /**
     * Get email.
     * 
     * @return String
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * Set email.
     * 
     * @param email that has to be set.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Get lastname.
     * 
     * @return String
     */
    public String getLastName() {
        return this.lastName;
    }

    /**
     * Set lastname.
     * 
     * @param lastName that has to be set.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Get firstname.
     * 
     * @return String
     */
    public String getFirstName() {
        return this.firstName;
    }

    /**
     * Set firstname.
     * 
     * @param firstName that has to be set.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public String toString() {
        return "SignUpRequest [birthdate=" + birthdate + ", email=" + email + ", firstName=" + firstName + ", gender="
                + gender + ", lastName=" + lastName + ", password=" + password + ", roles=" + roles + "]";
    }

}
