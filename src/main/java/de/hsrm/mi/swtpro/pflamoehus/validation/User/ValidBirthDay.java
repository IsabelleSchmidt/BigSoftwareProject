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
@Constraint(validatedBy = ValidBirthDayValidator.class)
@Documented
public @interface ValidBirthDay {
    String message() default "You have to be 18 or older.";

    Class<? extends Payload>[] payload() default{ };
    Class<?>[] groups() default{ };
}
