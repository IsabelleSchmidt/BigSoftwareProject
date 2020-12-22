package de.hsrm.mi.swtpro.pflamoehus.db_test_user.db_test_adress;

import javax.validation.ValidatorFactory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.util.Set;

import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.springframework.boot.test.context.SpringBootTest;

import de.hsrm.mi.swtpro.pflamoehus.user.adress.Adress;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

@SpringBootTest
public class AdressValueTests {

    private final String STREETNAME = "Erlenstraße";
    private final String WRONG_STREETNAME = "e123";
    private final String HOUSENUMBER = "15a";
    private final String WORNG_HOUSENUMBER = "a15";
    private final String POSTCODE = "55433";
    private final String WRONG_POSTCODE = "546666";
    private final String CITY = "Wiesbaden";
    private final String WRONG_CITY = "W";

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

        adr.setStreetName(STREETNAME);
        adr.setHouseNumber(HOUSENUMBER);
        adr.setCity(CITY);
        adr.setPostCode(POSTCODE);

        assertThat(adr.toString()).contains(STREETNAME).contains(HOUSENUMBER).contains(CITY)
                .contains(String.valueOf(POSTCODE));
        assertThat(validator.validate(adr)).isEmpty();
    }

    @Test
    @DisplayName("create Adress with false values")
    public void adress_data() {

        Adress adr = new Adress();
        int nrWrongValues = 4;

        adr.setStreetName(WRONG_STREETNAME);
        adr.setHouseNumber(WORNG_HOUSENUMBER);
        adr.setCity(WRONG_CITY);
        adr.setPostCode(WRONG_POSTCODE);

        Set<ConstraintViolation<Adress>> violations;
        violations = validator.validate(adr);
        assertEquals(violations.size(),nrWrongValues);
    }

}
