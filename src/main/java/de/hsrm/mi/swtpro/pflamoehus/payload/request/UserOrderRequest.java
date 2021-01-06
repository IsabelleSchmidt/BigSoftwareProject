package de.hsrm.mi.swtpro.pflamoehus.payload.request;

import java.time.LocalDate;

import javax.validation.Valid;
import javax.validation.constraints.Future;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import de.hsrm.mi.swtpro.pflamoehus.user.adress.Adress;
import de.hsrm.mi.swtpro.pflamoehus.validation.user_db.ValidCreditCardNumber;

public class UserOrderRequest {

    @Valid
    private Adress adress;

    @Pattern(regexp = "^(DE\\d{2}[ ]\\d{4}[ ]\\d{4}[ ]\\d{4}[ ]\\d{4}[ ]\\d{2}|DE\\d{20}$))")
    private String iban;

    @Size(min = 3)
    private String bankcard_owner;

    @Size(min = 3)
    private String bank;

    private String creditcard_owner;

    @ValidCreditCardNumber
    private String creditcardnumber;

    @Future
    private LocalDate dateOfExpiry;

    private String token;

    public Adress getAdress() {
        return adress;
    }

    public void setAdress(Adress adress) {
        this.adress = adress;
    }

    @Override
    public String toString() {
        return "UserOrderRequest [adress=" + adress + ", bank=" + bank + ", bankcard_owner=" + bankcard_owner
                + ", creditcard_owner=" + creditcard_owner + ", creditcardnumber=" + creditcardnumber
                + ", dateOfExpiry=" + dateOfExpiry + ", iban=" + iban + ", token=" + token + "]";
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getBankcard_owner() {
        return bankcard_owner;
    }

    public void setBankcard_owner(String bankcard_owner) {
        this.bankcard_owner = bankcard_owner;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getCreditcard_owner() {
        return creditcard_owner;
    }

    public void setCreditcard_owner(String creditcard_owner) {
        this.creditcard_owner = creditcard_owner;
    }

    public String getCreditcardnumber() {
        return creditcardnumber;
    }

    public void setCreditcardnumber(String creditcardnumber) {
        this.creditcardnumber = creditcardnumber;
    }

    public LocalDate getDateOfExpiry() {
        return dateOfExpiry;
    }

    public void setDateOfExpiry(LocalDate dateOfExpiry) {
        this.dateOfExpiry = dateOfExpiry;
    }

    

}
