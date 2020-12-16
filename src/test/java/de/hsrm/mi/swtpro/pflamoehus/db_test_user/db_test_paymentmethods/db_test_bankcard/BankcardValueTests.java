package de.hsrm.mi.swtpro.pflamoehus.db_test_user.db_test_paymentmethods.db_test_bankcard;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.springframework.boot.test.context.SpringBootTest;

import de.hsrm.mi.swtpro.pflamoehus.user.paymentmethods.Bankcard;
@SpringBootTest
public class BankcardValueTests {
    
    public final String IBAN = "DE89 3704 0044 0532 0130 00";
    public final String WRONG_IBAN = "DE89 3 0044 0532 0130 00";
    public final String OWNER = "Steven Bob";
    public final String WRONG_OWNER = "Bob";
    public final String BANK = "Sparkasse";
    public final String WRONG_BANK = "bob";

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

        bc.setBank(BANK);
        bc.setIban(IBAN);
        bc.setOwner(OWNER);
        assertThat(bc.toString())
        .contains(BANK)
        .contains(IBAN)
        .contains(OWNER);

        assertThat(validator.validate(bc)).isEmpty();
    }

    @Test
    @DisplayName("try to create bankcard with wrong values")
    public void check_picture_validation() {
        
        Bankcard bc = new Bankcard();
        int nrWrongValues = 3;

        bc.setBank(WRONG_BANK);
        bc.setIban(WRONG_IBAN);
        bc.setOwner(WRONG_OWNER);

        Set<ConstraintViolation<Bankcard>> violations;
        violations = validator.validate(bc);
        assertThat(violations.size() == nrWrongValues);
                

    }
}
