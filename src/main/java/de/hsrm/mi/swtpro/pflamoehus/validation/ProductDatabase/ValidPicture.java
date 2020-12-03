package de.hsrm.mi.swtpro.pflamoehus.validation.ProductDatabase;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * Triggers a message if an wrong Picture is entered.
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidPictureValidator.class)
@Documented
public @interface ValidPicture {
    String message() default "Es werden nur Links zu .png und .jpg/.jpeg Dateien akzeptiert.";

    Class<? extends Payload>[] payload() default{ };
    Class<?>[] groups() default{ };
        
}
