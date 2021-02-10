package de.hsrm.mi.swtpro.pflamoehus.db_test_user.db_test_adress;

import javax.validation.ValidatorFactory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.springframework.boot.test.context.SpringBootTest;

import de.hsrm.mi.swtpro.pflamoehus.user.adress.Adress;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
public class AdressValueTests {

    private final String CORRECT_STREETNAME = "Erlenstraße";
    private final String INCORRECT_STREETNAME = "e123";
    private final String CORRECT_HOUSENUMBER = "15a";
    private final String INCORRECT_HOUSENUMBER = "a15";
    private final String CORRECT_POSTCODE = "55433";
    private final String INCORRECT_POSTCODE = "546666";
    private final String CORRECT_CITY = "Wiesbaden";
    private final String INCORRECT_CITY = "W";

    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();

    @AfterAll
    public void close() {
        factory.close();
    }

    @Test
    @DisplayName("create Adress with correct values")
    public void create_adress() {
        Adress adr = new Adress();

        adr.setStreetName(CORRECT_STREETNAME);
        adr.setHouseNumber(CORRECT_HOUSENUMBER);
        adr.setCity(CORRECT_CITY);
        adr.setPostCode(CORRECT_POSTCODE);

        assertThat(adr.toString()).contains(CORRECT_STREETNAME).contains(CORRECT_HOUSENUMBER).contains(CORRECT_CITY)
                .contains(String.valueOf(CORRECT_POSTCODE));
        assertThat(validator.validate(adr)).isEmpty();
    }

    @Test
    @DisplayName("create Adress with false values")
    public void adress_data() {

        Adress adr = new Adress();
        int nrWrongValues = 4;

        adr.setStreetName(INCORRECT_STREETNAME);
        adr.setHouseNumber(INCORRECT_HOUSENUMBER);
        adr.setCity(INCORRECT_CITY);
        adr.setPostCode(INCORRECT_POSTCODE);

        Set<ConstraintViolation<Adress>> violations;
        violations = validator.validate(adr);
        assertEquals(violations.size(),nrWrongValues);
    }

}
