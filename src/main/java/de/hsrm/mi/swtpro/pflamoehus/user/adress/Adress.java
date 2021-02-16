package de.hsrm.mi.swtpro.pflamoehus.user.adress;

import java.util.HashSet;
import java.util.Set;
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

    @NotNull(message="Der Straßenname muss angegeben werden.")
    @Pattern(regexp = "((\\p{L})+\\s?[-]?(\\p{L})+)+", message="Der Straßenname ist ungültig.")
    private String streetName;

    
    @NotNull(message="Die Hausnummer muss angegeben werden.")
    @Pattern(regexp = "\\d+?[a-zA-Z]?$", message="Die Hausnummer ist ungültig.")
    private String houseNumber;

    @NotNull(message="Die Postleitzahl muss angegeben werden,")
    @Pattern(regexp = "^[1-9]{1}[0-9]{4}$", message="Die Postleitzahl ist unültig.")
    private String postCode;

    @NotNull(message="Der Wohnort muss angegeben werden")
    @Pattern(regexp = "((\\p{L})+\\s?[-]?(\\p{L})+)+", message="Der Wohnort ist ungültig.")
    private String city;

    @ManyToMany(mappedBy = "allAdresses", fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<User> user = new HashSet<>();

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
     * To generate a adress as a string.
     * 
     * @return String
     */
    @Override
    public String toString() {
        return "Adress [adressID=" + adressID + ", city=" + city + ", houseNumber=" + houseNumber + ", postCode="
                + postCode + ", streetName=" + streetName  + ", version=" + version + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((city == null) ? 0 : city.hashCode());
        result = prime * result + ((houseNumber == null) ? 0 : houseNumber.hashCode());
        result = prime * result + ((postCode == null) ? 0 : postCode.hashCode());
        result = prime * result + ((streetName == null) ? 0 : streetName.hashCode());
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
        Adress other = (Adress) obj;
        if (city == null) {
            if (other.city != null)
                return false;
        } else if (!city.equals(other.city))
            return false;
        if (houseNumber == null) {
            if (other.houseNumber != null)
                return false;
        } else if (!houseNumber.equals(other.houseNumber))
            return false;
        if (postCode == null) {
            if (other.postCode != null)
                return false;
        } else if (!postCode.equals(other.postCode))
            return false;
        if (streetName == null) {
            if (other.streetName != null)
                return false;
        } else if (!streetName.equals(other.streetName))
            return false;
        if (user == null) {
            if (other.user != null)
                return false;
        } else if (!user.equals(other.user))
            return false;
        return true;
    }



}
