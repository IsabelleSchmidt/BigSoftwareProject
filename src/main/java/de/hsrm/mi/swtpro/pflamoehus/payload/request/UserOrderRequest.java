package de.hsrm.mi.swtpro.pflamoehus.payload.request;


import javax.validation.Valid;

import de.hsrm.mi.swtpro.pflamoehus.payload.response.JwtResponse;
import de.hsrm.mi.swtpro.pflamoehus.user.adress.Adress;
import de.hsrm.mi.swtpro.pflamoehus.user.paymentmethods.Bankcard;
import de.hsrm.mi.swtpro.pflamoehus.user.paymentmethods.Creditcard;

public class UserOrderRequest {

    @Valid
    private Adress adress;

    @Valid
    private Creditcard creditCard;

    @Valid
    private Bankcard bankCard;

    private JwtResponse token;

    public Adress getAdress() {
        return adress;
    }

    public void setAdress(Adress adress) {
        this.adress = adress;
    }


    public JwtResponse getToken() {
        return token;
    }

    public void setToken(JwtResponse token) {
        this.token = token;
    }

    public Creditcard getCreditcard() {
        return creditCard;
    }

    public void setCreditcard(Creditcard creditcard) {
        this.creditCard = creditcard;
    }

    public Bankcard getBankCard() {
        return bankCard;
    }

    public void setBankCard(Bankcard bankCard) {
        this.bankCard = bankCard;
    }

    @Override
    public String toString() {
        return "UserOrderRequest [adress=" + adress + ", bankCard=" + bankCard + ", creditcard=" + creditCard
                + ", token=" + token + "]";
    }

    
    

}
