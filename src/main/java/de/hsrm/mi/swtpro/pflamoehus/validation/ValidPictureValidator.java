package de.hsrm.mi.swtpro.pflamoehus.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidPictureValidator implements ConstraintValidator<ValidPicture, String> {

    
    /** 
     * @param value passed value
     * @param context Provides contextual data and operation
     * @return boolean if the entered Picture is valid or not
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        //Der forward slash koennte hier noch Probleme bereiten
        Pattern picturePattern =Pattern.compile("(\\D|\\|\\d|/)+.((jpe?g)|(png))");
        Matcher matcher = picturePattern.matcher(value);
        return matcher.matches();
    }
    
}
