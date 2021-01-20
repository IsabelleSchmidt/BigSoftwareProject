package de.hsrm.mi.swtpro.pflamoehus.payload.request;


import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import de.hsrm.mi.swtpro.pflamoehus.payload.response.JwtResponse;
import de.hsrm.mi.swtpro.pflamoehus.user.adress.Adress;
import de.hsrm.mi.swtpro.pflamoehus.user.paymentmethods.Bankcard;
import de.hsrm.mi.swtpro.pflamoehus.user.paymentmethods.Creditcard;
/*
 * UserOrderRequest for sending user information
 * 
 * @author Ann-Cathrin Fabian
 * @version 2
 */
@Validated
public class UserOrderRequest {
    //TODO: überall gucken ob liste oder hashset
    @Valid
    private Creditcard creditcard;

    @Valid
    private Adress adress;

    @Valid
    private Bankcard bankCard;

    @NotNull
    private JwtResponse token;

    
    /** 
     * Get adress.
     * 
     * @return Adress
     */
    public Adress getAdress() {
        return adress;
    }

    
    /** 
     * Set adress.
     * 
     * @param adress adress to be set
     */
    public void setAdress(Adress adress) {
        this.adress = adress;
    }


    
    /** 
     * Get token.
     * 
     * @return JwtResponse
     */
    public JwtResponse getToken() {
        return token;
    }

    
    /** 
     * Set token.
     * 
     * @param token to be set
     */
    public void setToken(JwtResponse token) {
        this.token = token;
    }

    
    /** 
     * Get creditcard.
     * 
     * @return Creditcard
     */
    public Creditcard getCreditcard() {
        return creditcard;
    }

    
    /** 
     * Set creditcard.
     * 
     * @param creditcard to be set
     */
    public void setCreditcard(Creditcard creditcard) {
        this.creditcard = creditcard;
    }

    
    /** 
     * Get bankcard.
     * 
     * @return Bankcard
     */
    public Bankcard getBankCard() {
        return bankCard;
    }

    
    /** 
     * Set bankcard.
     * 
     * @param bankCard to be set
     */
    public void setBankCard(Bankcard bankCard) {
        this.bankCard = bankCard;
    }

    
    /** 
     * UserOrderRequest to string.
     * 
     * @return String
     */
    @Override
    public String toString() {
        return "UserOrderRequest [adress=" + adress + ", bankCard=" + bankCard + ", creditcard=" + creditcard
                + ", token=" + token + "]";
    }

    
    

}
