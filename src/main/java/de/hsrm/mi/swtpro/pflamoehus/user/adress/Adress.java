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
    @Pattern(regexp = "\\p{L}+")
    private String streetName;

    @NotEmpty
    @Pattern(regexp = "[^\\s]+(\\.(?i)((jpg)|(png)))")
    private String houseNumber;

    @NotEmpty
    @Digits(integer = 5, fraction = 0)
    private int postCode;

    @NotEmpty
    @Pattern(regexp = "\\p{L}+")
    private String city;

    @OneToMany(mappedBy = "adress")
    private List<User> user;
    
}
