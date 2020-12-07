package de.hsrm.mi.swtpro.pflamoehus.user.paymentmethods;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Version;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import de.hsrm.mi.swtpro.pflamoehus.user.User;

@Entity
public class Bankcard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private long id;

    @Version
    @JsonIgnore
    private long version;

    //TODO: Validator?, je nach Land gibt es naemlich unterschiedlich viele Zahlen in der Kontonummer
    @NotEmpty
    @Pattern(regexp="((DE)\\d{2}( \\d{4}){4} \\d{2}")
    private String iban;

    @NotEmpty
    @Size(min=6)
    private String owner;

    @NotEmpty
    @Size(min=3)
    private String bank;

    @ManyToOne
    private User user;

    public long getId() {
        return this.id;
    }

    
    public long getVersion() {
        return this.version;
    }


    public String getIban() {
        return this.iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getOwner() {
        return this.owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getBank() {
        return this.bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }
    
}
