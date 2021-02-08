package de.hsrm.mi.swtpro.pflamoehus.validation.product_db;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/*
 * ValidPictureValidator
 * Compares a given picture with a pattern. The path has to at least end with .jpg or .png.
 * 
 * @author Svenja Schenk
 * @version 2
 */
 public class ValidPictureValidator implements ConstraintValidator<ValidPicture, String> {

    /**
     * @param value given picture
     * @param context contextual data
     * @return valid or not
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        Pattern picturePattern = Pattern
                .compile("^(\\\\(\\w|\\\\|\\d|\\.)+\\.((jpe?g)|(png)))|(\\/(\\w|\\/|\\d|\\.)+\\.((jpe?g)|(png)))");
        Matcher matcher = picturePattern.matcher(value);


        return matcher.matches();
    }

}
