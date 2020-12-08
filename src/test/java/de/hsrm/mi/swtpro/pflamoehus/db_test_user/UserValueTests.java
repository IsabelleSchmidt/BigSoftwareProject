package de.hsrm.mi.swtpro.pflamoehus.db_test_user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import de.hsrm.mi.swtpro.pflamoehus.user.User;


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class UserValueTests {
    //TODO: Email und Passwort Validatoren anpassen.
    
    private final String FIRSTNAME = "Olaf der Dritte";
    private final String GENDER = "DIVERSE";
    private final String PASSWOrD = "HaaaaaaaHA11!";
    private final String LASTNAME = "Schmidt";
    private final LocalDate BIRTHDAY = LocalDate.of(1999, 1, 1);
    private final LocalDate WRONG_BIRTHDAY = LocalDate.of(2018, 3, 3);
    private final String EMAIL = "abc@hs-rm.de";
    private final String WRONG_EMAIL = "3@2";
    private final String WRONG_PASSWORD = "ahahasdhasdnsjdckjdvkjbcvdkjbfvd";
    private final String WRONG_GENDER ="Male";

    private ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private Validator validator = factory.getValidator();

    @AfterAll
    private void clear(){
        factory.close();
    }

    @Test
    @DisplayName("create a new User with correct Values")
    public void create_User(){
        User user1 = new User();
        user1.setBirthdate(BIRTHDAY);
        user1.setEmail(EMAIL);
        user1.setFirstName(FIRSTNAME);
        user1.setLastName(LASTNAME);
        user1.setGender(GENDER);
        user1.setPassowrd(PASSWOrD);
       

    
        assertThat(user1.toString()).contains(EMAIL)
                                    .contains(LASTNAME)
                                    .contains(FIRSTNAME)
                                    .contains(BIRTHDAY.toString())
                                    .contains(GENDER)
                                    .contains(PASSWOrD)
                                    ;

                              
        assertTrue(validator.validate(user1).isEmpty());
    }

    @Test
    @DisplayName("create a new User with wrong values")
    public void create_User_wrong_values(){
        int numberWrongValues;
        Set<ConstraintViolation<User>> violations;
        User user2 = new User(); 
        //adding correct values
        user2.setFirstName(FIRSTNAME);
        user2.setLastName(LASTNAME);

        //adding false values

        user2.setBirthdate(WRONG_BIRTHDAY);
        user2.setPassowrd(WRONG_PASSWORD);
        user2.setGender(WRONG_GENDER);
        user2.setEmail(WRONG_EMAIL); 

       
        numberWrongValues = 4;
        violations = validator.validate(user2);
        assertTrue(violations.size() == numberWrongValues,"Anzahl falsch: "+Integer.toString(violations.size()));
        

        user2.setPassowrd(PASSWOrD);
        
        violations = validator.validate(user2);
        numberWrongValues--;
        assertTrue(violations.size()==numberWrongValues,"Passwort nicht richtig validiert.");

        user2.setLastName("1");
        numberWrongValues++;
        violations = validator.validate(user2);
        assertTrue(violations.size() == numberWrongValues,"Lastname nicht richtig validiert.");

        user2.setEmail(EMAIL);
        numberWrongValues--;
        violations = validator.validate(user2);
        assertTrue(violations.size()==numberWrongValues,"Email nicht richtig validiert.");

    }
}
