package de.hsrm.mi.swtpro.pflamoehus.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidProductTypeValidator.class)
@Documented
public @interface ValidProductType {
    String message() default "Das ist kein existierender Produkttyp. Folgende Typen stehen zur Auswahl:";
    
}
