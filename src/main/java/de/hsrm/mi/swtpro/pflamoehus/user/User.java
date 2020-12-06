package de.hsrm.mi.swtpro.pflamoehus.user;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Version;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;


import de.hsrm.mi.swtpro.pflamoehus.user.adress.Adress;
import de.hsrm.mi.swtpro.pflamoehus.user.paymentmethods.Bankcard;
import de.hsrm.mi.swtpro.pflamoehus.user.paymentmethods.Creditcard;
import de.hsrm.mi.swtpro.pflamoehus.validation.User.ValidBirthDay;
import de.hsrm.mi.swtpro.pflamoehus.validation.User.ValidGender;
import de.hsrm.mi.swtpro.pflamoehus.validation.User.ValidPassword;


@Entity
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private long id;

    @Version
    @JsonIgnore
    private long version;
    
    @NotEmpty
    @Column(name="EMAIL", unique=true)
    @Email
    private String email;
    
    @NotEmpty
    @ValidPassword
    @JsonProperty(access =  Access.WRITE_ONLY)
    private String passwort;

    //Hab die stringlänge auf min. 3 gesetzt
    @NotEmpty @Size(min=3)
    private String firstName;

    //vllt in einem string mit dem vornamen lassen? Es gibt ja auch leute mit 3 oder 4 vornamen
    private String middleName;

    @NotEmpty @Size(min=2)
    private String lastName;

    //Sollen wir mehrere Adressen speichern koennen statt nur einer? vielleicht will man seiner mutter mal was schicken etc.
    @NotEmpty
    @ValidBirthDay
    private LocalDate birthdate;

    //Wenn das hier notempty ist müssen wir was reinschreiben wenn wir es erstellen wollen, geht das dann mit der data sql?
    @NotEmpty
    @ManyToOne
    private Adress adress;

    @NotEmpty
    @ValidGender
    private String gender;

    @Valid
    @OneToMany(mappedBy = "user")
    private List<Bankcard> bankcard;

    @Valid
    @OneToMany(mappedBy = "user")
    private List<Creditcard> creditcard;

    public String getGender() {
        return this.gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Adress getAdress() {
        return this.adress;
    }

    public void setAdress(Adress adress) {
        this.adress = adress;
    }

    public LocalDate getBirthdate() {
        return this.birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public long getVersion() {
        return this.version;
    }


    public long getId() {
        return this.id;
    }


    public String getMiddleName() {
        return this.middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

}
