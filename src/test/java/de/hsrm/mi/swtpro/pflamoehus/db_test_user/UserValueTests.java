package de.hsrm.mi.swtpro.pflamoehus.db_test_user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import de.hsrm.mi.swtpro.pflamoehus.user.Gender;
import de.hsrm.mi.swtpro.pflamoehus.user.User;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(Lifecycle.PER_CLASS)
public class UserValueTests {

    private final String FIRSTNAME = "Olaf der Dritte";
    private final Gender GENDER = Gender.DIVERSE;
    private final String PASSWOrD = "HaaaaaaaHA11!";
    private final String LASTNAME = "Schmidt";
    private final LocalDate BIRTHDAY = LocalDate.of(1999, 1, 1);
    private final LocalDate WRONG_BIRTHDAY = LocalDate.of(2018, 3, 3);
    private final String EMAIL = "hansolaf@hs-rm.de";
    private final String WRONG_EMAIL = "3@2";

    private ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private Validator validator = factory.getValidator();

    @AfterAll
    private void clear() {
        factory.close();
    }

    @Test
    @DisplayName("create a new User with correct Values")
    public void create_User() {
        User user1 = new User();
        user1.setBirthdate(BIRTHDAY);
        user1.setEmail(EMAIL);
        user1.setFirstName(FIRSTNAME);
        user1.setLastName(LASTNAME);
        user1.setGender(GENDER);
        user1.setPassword(PASSWOrD);

        assertThat(user1.toString()).contains(EMAIL).contains(LASTNAME).contains(FIRSTNAME)
                .contains(BIRTHDAY.toString()).contains(GENDER.toString()).contains(PASSWOrD);

        assertTrue(validator.validate(user1).isEmpty());
    }

    @Test
    @DisplayName("create a new User with wrong values")
    public void create_User_wrong_values() {

        User user2 = new User();
        // adding correct values
        user2.setFirstName(FIRSTNAME);
        user2.setLastName(LASTNAME);
        user2.setPassword(PASSWOrD);
        user2.setGender(GENDER);
        user2.setEmail(EMAIL);
        // adding false values

        user2.setBirthdate(WRONG_BIRTHDAY);

        assertFalse(validator.validate(user2).isEmpty(), "Geburtsdatum muss als falsch validiert sein.");
        user2.setBirthdate(BIRTHDAY);
        user2.setPassword(PASSWOrD);
        user2.setEmail(WRONG_EMAIL);
        assertFalse(validator.validate(user2).isEmpty(), "Mail muss als falsch validiert werden.");
        user2.setEmail(EMAIL);

    }
}
