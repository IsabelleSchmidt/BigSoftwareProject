package de.hsrm.mi.swtpro.pflamoehus.validation.user_db;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidPasswordValidator.class)
@Documented
public @interface ValidPassword{
    String message() default "The password needs to contain at least one upper case, one lower case letter, a sign and a number. Furthermore, the password needs to be at least 8 and at maximum 32 characters long.";

    Class<? extends Payload>[] payload() default{ };
    Class<?>[] groups() default{ };

}