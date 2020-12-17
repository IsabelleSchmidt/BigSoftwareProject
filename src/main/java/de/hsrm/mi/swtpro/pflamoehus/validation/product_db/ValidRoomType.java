package de.hsrm.mi.swtpro.pflamoehus.validation.product_db;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * Triggers a message if an wrong RoomType is entered.
 */
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidRoomTypeValidator.class)
@Documented
public @interface ValidRoomType {
    String message() default "This is not an existing room type. The following types are available: bathroom, bedroom, living room, kitchen / eat-in kitchen, children's room, study";

    Class<? extends Payload>[] payload() default {};

    Class<?>[] groups() default {};

}
