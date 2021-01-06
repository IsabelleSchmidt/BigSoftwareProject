package de.hsrm.mi.swtpro.pflamoehus.authentication.payload.request;

import javax.validation.Valid;

import de.hsrm.mi.swtpro.pflamoehus.user.adress.Adress;
import de.hsrm.mi.swtpro.pflamoehus.user.paymentmethods.Bankcard;
import de.hsrm.mi.swtpro.pflamoehus.user.paymentmethods.Creditcard;

public class UserOrderRequest {

    @Valid
    private Adress adress;

    @Valid
    private Bankcard bankcard;

    @Valid
    private Creditcard creditcard;

    private String token;

    public Adress getAdress() {
        return adress;
    }

    public void setAdress(Adress adress) {
        this.adress = adress;
    }

    public Bankcard getBankcard() {
        return bankcard;
    }

    public void setBankcard(Bankcard bankcard) {
        this.bankcard = bankcard;
    }

    public Creditcard getCreditcard() {
        return creditcard;
    }

    public void setCreditcard(Creditcard creditcard) {
        this.creditcard = creditcard;
    }

    @Override
    public String toString() {
        return "UserOrderRequest [adress=" + adress + ", bankcard=" + bankcard + ", creditcard=" + creditcard
                + ", token=" + token + "]";
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    

}
