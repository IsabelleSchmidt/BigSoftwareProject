package de.hsrm.mi.swtpro.pflamoehus.user.adress;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonIgnore;

import de.hsrm.mi.swtpro.pflamoehus.user.User;

/*
 * Adress entity for its database.
 * 
 * @author Ann-Cathrin Fabian
 * @version 1
 */
@Entity
public class Adress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private long adressID;

    @Version
    @JsonIgnore
    private long version;

    @NotNull(message="EMPTY:Der Straßenname muss angegeben werden.")
    @Pattern(regexp = "\\p{L}{2,}", message="NOTVALID:Der Straßennamen ist ungültig.")
    private String streetName;

    
    @NotNull(message="EMPTY:Die Hausnummer muss angegeben werden.")
    @Pattern(regexp = "\\d+?[a-zA-Z]?$", message="NOTVALID:Die Hausnummer ist ungültig.")
    private String houseNumber;

    @NotNull(message="EMPTY:Die Postleitzahl muss angegeben werden,")
    @Pattern(regexp = "^[1-9]{1}[0-9]{4}$", message="NOTVALID:Die Postleitzahl ist unültig.")
    private String postCode;

    @NotNull(message="EMPTY:Der Wohnort muss angegeben werden")
    @Pattern(regexp = "\\p{L}{2,}", message="NOTVALID:Der Wohnort ist ungültig.")
    private String city;

    @ManyToMany(mappedBy = "allAdresses", fetch = FetchType.LAZY)
    private List<User> user;

    /**
     * Get id.
     * 
     * @return id
     */
    public long getId() {
        return adressID;
    }

    /**
     * Get version.
     * 
     * @return version
     */
    public long getVersion() {
        return version;
    }

    /**
     * Get streetname.
     * 
     * @return streetname
     */
    public String getStreetName() {
        return streetName;
    }

    /**
     * Set streetname.
     * 
     * @param streetName streetname that has to be set
     */
    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    /**
     * Get housenumber.
     * 
     * @return housenumber
     */
    public String getHouseNumber() {
        return houseNumber;
    }

    /**
     * Set housenumber.
     * 
     * @param houseNumber housenumber that has to be set
     */
    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    /**
     * Get postcode.
     * 
     * @return postcode
     */
    public String getPostCode() {
        return postCode;
    }

    /**
     * Set postcode.
     * 
     * @param postCode postcode that has to be set
     */
    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    /**
     * Get city.
     * 
     * @return city
     */
    public String getCity() {
        return city;
    }

    /**
     * Set city.
     * 
     * @param city city that has to be set
     */
    public void setCity(String city) {
        this.city = city;
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
     * To generate a adress as a string.
     * 
     * @return String
     */
    @Override
    public String toString() {
        return "Adress [adressID=" + adressID + ", city=" + city + ", houseNumber=" + houseNumber + ", postCode="
                + postCode + ", streetName=" + streetName + ", user=" + user + ", version=" + version + "]";
    }

}
