package de.hsrm.mi.swtpro.pflamoehus.validation.user;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class ValidCreditCardNumberValidator implements ConstraintValidator<ValidCreditCardNumber, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        Pattern masterCard = Pattern.compile("^(5[1-5][0-9]{14}|2(22[1-9][0-9]{12}|2[3-9][0-9]{13}|[3-6][0-9]{14}|7[0-1][0-9]{13}|720[0-9]{12}))$)");
        Pattern visaCard = Pattern.compile("^4[0-9]{12}(?:[0-9]{3})?$)");
        Pattern americanExpress = Pattern.compile("^3[47][0-9]{13}$");
        Pattern dinersClubInternational = Pattern.compile("^3(?:0[0-5]|[68][0-9])[0-9]{11}$");

        Matcher matcher1 = masterCard.matcher(value);
        Matcher matcher2 = visaCard.matcher(value);
        Matcher matcher3 = americanExpress.matcher(value);
        Matcher matcher4 = dinersClubInternational.matcher(value);
        
        
        if(matcher1.matches()){
            return true;
        }else if(matcher2.matches()){
            return true;
        }else if(matcher3.matches()){
            return true;
        }else if(matcher4.matches()){
            return true;
        }else{
            return false;
        }
    }
    
}
