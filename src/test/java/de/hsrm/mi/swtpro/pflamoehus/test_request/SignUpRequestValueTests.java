package de.hsrm.mi.swtpro.pflamoehus.test_request;

import java.time.LocalDate;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import de.hsrm.mi.swtpro.pflamoehus.payload.request.SignUpRequest;
import de.hsrm.mi.swtpro.pflamoehus.user.Gender;

@SpringBootTest
public class SignUpRequestValueTests {
    
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();

   private final String CORRECT_FIRSTNAME = "Paul";
   private final String INCORRECT_FIRSTNAME="a";
   private final String CORRECT_LASTNAME = "Herbert";
   private final String INCORRECT_LASTNAME = "a";
   private final String CORRECT_EMAIL = "user@pflamoehus.de";
   private final String INCORRECT_EMAIL = "aaaa@aaaaa";
   private final String CORRECT_PASSWORD = "Ahahaha123.";
   private final String INCORRECT_PASSWORD ="aaaaaaaaaaaaa";
   private final Gender CORRECT_GENDER = Gender.DIVERSE;
   private final LocalDate CORRECT_BIRTHDATE = LocalDate.of(2000, 01, 01);
   private final LocalDate INCORRECT_BIRTHDATE = LocalDate.of(2020,01,01);
   

    @AfterAll
    public void closeFactory(){
        factory.close();
    }


    @Test
    @DisplayName("correct values should not cause validationerrors.")
    public void correct_values_cause_no_errors(){
        SignUpRequest request = new SignUpRequest();

        request.setBirthdate(CORRECT_BIRTHDATE);
        request.setEmail(CORRECT_EMAIL);
        request.setPassword(CORRECT_PASSWORD);
        request.setFirstName(CORRECT_FIRSTNAME);
        request.setLastName(CORRECT_LASTNAME);
        request.setGender(CORRECT_GENDER);
        
        assertEquals(0,validator.validate(request).size());
    }

    
    @Test
    @DisplayName("incorrect values should cause validationerrors.")
    public void incorrect_values_cause_errors(){
        SignUpRequest request = new SignUpRequest();

        request.setBirthdate(CORRECT_BIRTHDATE);
        request.setEmail(CORRECT_EMAIL);
        request.setPassword(CORRECT_PASSWORD);
        request.setFirstName(CORRECT_FIRSTNAME);
        request.setLastName(CORRECT_LASTNAME);
        request.setGender(CORRECT_GENDER);
        
        assertEquals(0,validator.validate(request).size());

        request.setBirthdate(INCORRECT_BIRTHDATE);
        assertEquals(1,validator.validate(request).size());

        request.setEmail(INCORRECT_EMAIL);
        assertEquals(2,validator.validate(request).size());

        request.setFirstName(INCORRECT_FIRSTNAME);
        assertEquals(3,validator.validate(request).size());

        request.setLastName(INCORRECT_LASTNAME);
        assertEquals(4,validator.validate(request).size());

        request.setPassword(INCORRECT_PASSWORD);
        assertEquals(5,validator.validate(request).size());
    }
}
