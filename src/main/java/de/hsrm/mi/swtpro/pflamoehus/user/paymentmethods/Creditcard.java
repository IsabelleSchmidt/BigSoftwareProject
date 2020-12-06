package de.hsrm.mi.swtpro.pflamoehus.user.paymentmethods;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Version;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

import de.hsrm.mi.swtpro.pflamoehus.user.User;

import java.time.LocalDate;

@Entity
public class Creditcard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private long id;

    @Version
    @JsonIgnore
    private long version;

    @NotEmpty
    private String owner;

    @NotEmpty
    private String creditcardnumber;

    @NotEmpty
    private LocalDate dateOfExpiry;

    @ManyToOne
    private User user;

    public long getId() {
        return this.id;
    }


    public long getVersion() {
        return this.version;
    }


    public String getOwner() {
        return this.owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getCreditcardnumber() {
        return this.creditcardnumber;
    }

    public void setCreditcardnumber(String creditcardnumber) {
        this.creditcardnumber = creditcardnumber;
    }

    public LocalDate getDateOfExperiy() {
        return this.dateOfExpiry;
    }

    public void setDateOfExperiy(LocalDate dateOfExperiy) {
        this.dateOfExpiry = dateOfExperiy;
    }
    
}
