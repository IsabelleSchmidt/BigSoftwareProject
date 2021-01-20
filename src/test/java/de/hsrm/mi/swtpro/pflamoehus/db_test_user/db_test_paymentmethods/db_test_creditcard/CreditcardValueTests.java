package de.hsrm.mi.swtpro.pflamoehus.db_test_user.db_test_paymentmethods.db_test_creditcard;

import java.time.LocalDate;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;
import de.hsrm.mi.swtpro.pflamoehus.user.paymentmethods.Creditcard;



@SpringBootTest
public class CreditcardValueTests {
    
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();

    final private String OWNER = "Christopf";
    final private String CREDITCARDNUMBER = "4111111111111";
    final private LocalDate EXPIRY = LocalDate.of(2023,10,04);

    final private String WRONG_OWNER ="";
    final private LocalDate WRONG_EXPIRY = LocalDate.of(2020,01,01);


    @AfterAll
    public void cleanup(){
        factory.close();
    }

    @Test
    @DisplayName("create Creditcard with correct values")
    public void create_correct_card(){

        Creditcard correct = new Creditcard();

        correct.setCowner(OWNER);
        correct.setCreditcardnumber(CREDITCARDNUMBER);
        correct.setDateOfExpiry(EXPIRY);

        assertThat(validator.validate(correct)).isEmpty();

    }

    @Test
    @DisplayName("create Creditcard with incorrect values")
    public void create_incorrect_card(){

        Creditcard incorrect = new Creditcard();
        incorrect.setCowner(OWNER);
        incorrect.setCreditcardnumber(CREDITCARDNUMBER);
        incorrect.setDateOfExpiry(EXPIRY);
        incorrect.setDateOfExpiry(WRONG_EXPIRY);
        assertThat(validator.validate(incorrect).size()).isEqualTo(1);
        incorrect.setCowner(WRONG_OWNER);
        assertThat(validator.validate(incorrect).size()).isEqualTo(3);



    }
    

}
