package de.hsrm.mi.swtpro.pflamoehus.db_test_user.db_test_paymentmethods.db_test_bankcard;

import static org.assertj.core.api.Assertions.assertThat;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.springframework.boot.test.context.SpringBootTest;

import de.hsrm.mi.swtpro.pflamoehus.user.paymentmethods.Bankcard;
@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
public class BankcardValueTests {
    
    public final String CORRECT_IBAN = "DE89 3704 0044 0532 0130 00";
    public final String INCORRECT_IBAN = "DE89 3 0044 0532 0130 00";
    public final String CORRECT_OWNER = "Steven Bob";
    public final String INCORRECT_OWNER = "Bob";
    public final String CORRECT_BANK = "Sparkasse";
    public final String INCORRECT_BANK = "bob";

    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();

    @AfterAll
    public void close() {
        factory.close();
    }

    @Test
    @DisplayName("create bankcard with correct values")
    public void create_bankcard() {
        Bankcard bc = new Bankcard();

        bc.setBank(CORRECT_BANK);
        bc.setIban(CORRECT_IBAN);
        bc.setOwner(CORRECT_OWNER);
        assertThat(bc.toString())
        .contains(CORRECT_BANK)
        .contains(CORRECT_IBAN)
        .contains(CORRECT_OWNER);

        assertThat(validator.validate(bc)).isEmpty();
    }

    @Test
    @DisplayName("try to create bankcard with wrong values")
    public void check_picture_validation() {
        
        Bankcard bc = new Bankcard();

        bc.setIban(INCORRECT_IBAN);

        assertThat(validator.validate(bc)).isNotEmpty();
                

    }
}
