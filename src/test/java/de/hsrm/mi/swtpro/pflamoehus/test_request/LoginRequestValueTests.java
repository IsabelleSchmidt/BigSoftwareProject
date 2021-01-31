package de.hsrm.mi.swtpro.pflamoehus.test_request;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.springframework.boot.test.context.SpringBootTest;

import de.hsrm.mi.swtpro.pflamoehus.payload.request.LoginRequest;

@SpringBootTest
public class LoginRequestValueTests {

    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();

    private final String CORRECT_EMAIL = "user@pflamoehus.de";
    private final String INCORRECT_EMAIL = "";
    private final String CORRECT_PASSWORD = "Ahahaha123.";
    private final String INCORRECT_PASSWORD ="";

    @AfterAll
    public void closeFactory(){
        factory.close();
    }

    @Test
    @DisplayName("Valid email and password should not cause validationerrors.")
    public void valid_email_and_password_cause_no_validationerrors(){
        LoginRequest request = new LoginRequest();

        request.setPassword(CORRECT_PASSWORD);
        request.setEmail(CORRECT_EMAIL);

        assertEquals(0,validator.validate(request).size());

    }

    @Test
    @DisplayName("Invalid email should cause a validationerror.")
    public void invalid_mail_causes_validationerror(){
        LoginRequest request = new LoginRequest();
        request.setEmail(INCORRECT_EMAIL);
        request.setPassword(CORRECT_PASSWORD);

        assertEquals(1, validator.validate(request).size());
    }
    

    @Test
    @DisplayName("Inalid password should  cause a validationerror.")
    public void invalid_password_causes_validationerror(){
        LoginRequest request = new LoginRequest();
        request.setEmail(CORRECT_EMAIL);
        request.setPassword(INCORRECT_PASSWORD);

        assertEquals(1, validator.validate(request).size());
    }

    
}
