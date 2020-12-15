package de.hsrm.mi.swtpro.pflamoehus.validation.product_db;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidPictureValidator implements ConstraintValidator<ValidPicture, String> {

    
    /** 
     * @param value passed value
     * @param context Provides contextual data and operation
     * @return boolean if the entered house number is valid or not
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {


        Pattern picturePattern = Pattern.compile("^\\\\(\\w|\\\\|\\d|\\.)+\\.((jpe?g)|(png))|\\/(\\w|\\/|\\d|\\.)+\\.((jpe?g)|(png))");
        Matcher matcher = picturePattern.matcher(value);
        return matcher.matches();
    }
    
}
