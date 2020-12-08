package de.hsrm.mi.swtpro.pflamoehus.user.adress;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Version;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonIgnore;

import de.hsrm.mi.swtpro.pflamoehus.user.User;


@Entity
public class Adress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private long id;

    @Version
    @JsonIgnore
    private long version;

    @NotEmpty
    @Pattern(regexp = "\\p{L}{2,}")
    private String streetName;

    @Pattern(regexp ="\\d+?[a-zA-Z]?$")
    @NotEmpty
    private String houseNumber;

    @NotEmpty
    @Digits(integer = 5, fraction = 0)
    private int postCode;

    @NotEmpty
    @Pattern(regexp = "\\p{L}{2,}")
    private String city;

 
    @OneToMany(mappedBy = "adress")
    private List<User> user;

    public long getId() {
        return id;
    }


    public long getVersion() {
        return version;
    }


    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public int getPostCode() {
        return postCode;
    }

    public void setPostCode(int postCode) {
        this.postCode = postCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public List<User> getUser() {
        return user;
    }

    public void setUser(List<User> user) {
        this.user = user;
    }
    
}
