package de.hsrm.mi.swtpro.pflamoehus.validation.user_db;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/*
 * ValidCreditCardNumberValidator
 * The given creditcard number has to equal one of the offical patterns of one of the most used credit card companies.
 * 
 * @author Ann-Cathrin Fabian
 * @version 1
 */
 public class ValidCreditCardNumberValidator implements ConstraintValidator<ValidCreditCardNumber, String> {

    
    /** 
     * @param value given creditcard number
     * @param context contextual data
     * @return valid or not
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        Pattern masterCard = Pattern.compile("^(5[1-5][0-9]{14}|2(22[1-9][0-9]{12}|2[3-9][0-9]{13}|[3-6][0-9]{14}|7[0-1][0-9]{13}|720[0-9]{12}))$");
        Pattern visaCard = Pattern.compile("^4[0-9]{12}(?:[0-9]{3})?$");
        Pattern americanExpress = Pattern.compile("^3[47][0-9]{13}$");
        Pattern dinersClubInternational = Pattern.compile("^3(?:0[0-5]|[68][0-9])[0-9]{11}$");

        Matcher matcher1 = masterCard.matcher(value);
        Matcher matcher2 = visaCard.matcher(value);
        Matcher matcher3 = americanExpress.matcher(value);
        Matcher matcher4 = dinersClubInternational.matcher(value);

        return matcher1.matches() || matcher2.matches() || matcher3.matches() || matcher4.matches();
    }

}
