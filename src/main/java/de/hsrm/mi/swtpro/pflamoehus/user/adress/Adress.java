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

@Entity
public class Adress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private long adressID;

    @Version
    @JsonIgnore
    private long version;

    @NotNull
    @Pattern(regexp = "\\p{L}{2,}")
    private String streetName;

    @Pattern(regexp = "\\d+?[a-zA-Z]?$")
    @NotNull
    private String houseNumber;

    @NotNull
    @Pattern(regexp = "^[1-9]{1}[0-9]{4}$")
    private String postCode;

    @NotNull
    @Pattern(regexp = "\\p{L}{2,}")
    private String city;

    @ManyToMany(mappedBy = "allAdresses", fetch = FetchType.LAZY)
    private List<User> user;

    //Getter and Setter
    /**
     * @return long
     */
    public long getId() {
        return adressID;
    }

    /**
     * @return long
     */
    public long getVersion() {
        return version;
    }

    /**
     * @return String
     */
    public String getStreetName() {
        return streetName;
    }

    /**
     * @param streetName
     */
    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    /**
     * @return String
     */
    public String getHouseNumber() {
        return houseNumber;
    }

    /**
     * @param houseNumber
     */
    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    /**
     * @return int
     */
    public String getPostCode() {
        return postCode;
    }

    /**
     * @param postCode
     */
    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    /**
     * @return String
     */
    public String getCity() {
        return city;
    }

    /**
     * @param city
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * @return List<User>
     */
    public List<User> getUser() {
        return user;
    }

    /**
     * @param user
     */
    public void setUser(List<User> user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Adress [adressID=" + adressID + ", city=" + city + ", houseNumber=" + houseNumber + ", postCode="
                + postCode + ", streetName=" + streetName + ", user=" + user + ", version=" + version + "]";
    }

    

}
