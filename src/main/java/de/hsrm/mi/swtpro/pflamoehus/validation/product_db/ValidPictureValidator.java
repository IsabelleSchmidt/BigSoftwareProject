package de.hsrm.mi.swtpro.pflamoehus.validation.product_db;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import de.hsrm.mi.swtpro.pflamoehus.product.picture.Picture;

/*
 * ValidPictureValidator
 * Compares a given picture with a pattern. The path has to at least end with .jpg or .png.
 * 
 * @author Svenja Schenk
 * @version 2
 */
 public class ValidPictureValidator implements ConstraintValidator<ValidPicture, Picture> {

    /**
     * @param value given picture
     * @param context contextual data
     * @return valid or not
     */
    @Override
    //TODO: muss eigentlich set sein
    public boolean isValid(Picture value, ConstraintValidatorContext context) {

        Pattern picturePattern = Pattern
                .compile("^(\\\\(\\w|\\\\|\\d|\\.)+\\.((jpe?g)|(png)))|(\\/(\\w|\\/|\\d|\\.)+\\.((jpe?g)|(png)))");
        Matcher matcher = picturePattern.matcher(value.getPath());


        return matcher.matches();
    }

}
