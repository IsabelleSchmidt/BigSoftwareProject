package de.hsrm.mi.swtpro.pflamoehus.validation.productDatabase;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * Triggers a message if an wrong ProductType is entered.
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidProductTypeValidator.class)
@Documented
public @interface ValidProductType {
    String message() default "Das ist kein existierender Produkttyp. Folgende Typen stehen zur Auswahl: Pflanze, Tisch, Bett, Stuhl, Dekoration, Schrank/Kommode, Sofa/Couch";

    Class<? extends Payload>[] payload() default{ };
    Class<?>[] groups() default{ };
        
}
