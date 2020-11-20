package de.hsrm.mi.swtpro.pflamoehus.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidRoomTypeValidator.class)
@Documented
public @interface ValidRoomType {
    String message() default "Das ist kein existierender Raumtyp. Folgende Typen stehen zur Auswahl: Bad, Schlafzimmer, Wohnzimmer, Küche/Wohnküche, Kinderzimmer, Arbeitszimmer";

    Class<? extends Payload>[] payload() default{ };
    Class<?>[] groups() default{ };
    
}
