package de.hsrm.mi.swtpro.pflamoehus.validation.User;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidGenderValidator.class)
@Documented
public @interface ValidGender {

    String message() default "You can be female, male or diverse";

    Class<? extends Payload>[] payload() default{ };
    Class<?>[] groups() default{ };
    
}
