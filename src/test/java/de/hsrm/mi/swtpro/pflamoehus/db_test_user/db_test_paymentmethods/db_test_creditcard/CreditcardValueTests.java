package de.hsrm.mi.swtpro.pflamoehus.db_test_user.db_test_paymentmethods.db_test_creditcard;

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
import static org.assertj.core.api.Assertions.assertThat;
import de.hsrm.mi.swtpro.pflamoehus.user.paymentmethods.Creditcard;



@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
public class CreditcardValueTests {
    
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();

    final private String CORRECT_OWNER = "Christopf";
    final private String CORRECT_CREDITCARDNUMBER = "4111111111111";
    final private LocalDate CORRECT_DATEOFEXPIRY = LocalDate.of(2023,10,04);
    final private String INCORRECT_OWNER ="";
    final private LocalDate WRONG_EXPIRY = LocalDate.of(2020,01,01);


    @AfterAll
    public void cleanup(){
        factory.close();
    }

    @Test
    @DisplayName("create Creditcard with correct values")
    public void create_correct_card(){

        Creditcard correct = new Creditcard();

        correct.setCowner(CORRECT_OWNER);
        correct.setCreditcardnumber(CORRECT_CREDITCARDNUMBER);
        correct.setDateOfExpiry(CORRECT_DATEOFEXPIRY);

        assertThat(validator.validate(correct)).isEmpty();

    }

    @Test
    @DisplayName("create Creditcard with incorrect values")
    public void create_incorrect_card(){

        Creditcard incorrect = new Creditcard();
        incorrect.setCowner(CORRECT_OWNER);
        incorrect.setCreditcardnumber(CORRECT_CREDITCARDNUMBER);
        incorrect.setDateOfExpiry(CORRECT_DATEOFEXPIRY);
        incorrect.setDateOfExpiry(WRONG_EXPIRY);
        assertThat(validator.validate(incorrect).size()).isEqualTo(1);
        incorrect.setCowner(INCORRECT_OWNER);
        assertThat(validator.validate(incorrect).size()).isEqualTo(3);



    }
    

}
