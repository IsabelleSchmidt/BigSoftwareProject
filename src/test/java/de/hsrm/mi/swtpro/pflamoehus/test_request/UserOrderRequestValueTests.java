package de.hsrm.mi.swtpro.pflamoehus.test_request;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.time.LocalDate;
import de.hsrm.mi.swtpro.pflamoehus.payload.request.UserOrderRequest;
import de.hsrm.mi.swtpro.pflamoehus.user.adress.Adress;
import de.hsrm.mi.swtpro.pflamoehus.user.paymentmethods.Bankcard;
import de.hsrm.mi.swtpro.pflamoehus.user.paymentmethods.Creditcard;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
public class UserOrderRequestValueTests {
    
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();

    //Adressattributes
    private final String CORRECT_STREETNAME = "Erlenstraße";
    private final String CORRECT_HOUSENUMBER = "15a";
    private final String CORRECT_POSTCODE = "55433";
    private final String CORRECT_CITY = "Wiesbaden";
    private final String INCORRECT_CITY = "W";

    //Creditcardattributes
    final private String CORRECT_OWNER = "Christopf";
    final private String CORRECT_CREDITCARDNUMBER = "4111111111111";
    final private LocalDate CORRECT_DATEOFEXPIRY = LocalDate.of(2023,10,04);
    final private String INCORRECT_OWNER ="h";

    //Bankcardattributes
    public final String CORRECT_IBAN = "DE89 3704 0044 0532 0130 00";
    public final String INCORRECT_IBAN = "DE89 3 0044 0532 0130 00";
    public final String CORRECT_BANKCARDOWNER = "Steven Bob";
    public final String CORRECT_BANK = "Sparkasse";

    private Adress adress = new Adress();
    private Creditcard creditcard = new Creditcard();
    private Bankcard bankcard = new Bankcard();

    @BeforeAll
    public void init(){

        adress.setCity(CORRECT_CITY);
        adress.setHouseNumber(CORRECT_HOUSENUMBER);
        adress.setPostCode(CORRECT_POSTCODE);
        adress.setStreetName(CORRECT_STREETNAME);

        bankcard.setOwner(CORRECT_BANKCARDOWNER);
        bankcard.setIban(CORRECT_IBAN);
        bankcard.setBank(CORRECT_BANK);

        creditcard.setDateOfExpiry(CORRECT_DATEOFEXPIRY);
        creditcard.setCowner(CORRECT_OWNER);
        creditcard.setCreditcardnumber(CORRECT_CREDITCARDNUMBER);
   

    }


    @AfterAll
    public void closeFactory(){
        factory.close();
    }

    @Test
    @DisplayName("incorrect attribute values shoould cause validationerrors for UserOrderRequest")
    public void correct_values_cause_no_errors(){
        UserOrderRequest request = new UserOrderRequest();
        request.setAdress(adress);
        request.setBankCard(bankcard);
        request.setCreditcard(creditcard);

        assertEquals(0,validator.validate(request).size());
    }

    @Test
    @DisplayName("correct values should not cause validationerrors for UserOrderRequest")
    public void incorrect_values_cause_errors(){

        UserOrderRequest request = new UserOrderRequest();
        adress.setCity(INCORRECT_CITY);
        request.setAdress(adress);
        request.setBankCard(bankcard);
        request.setCreditcard(creditcard);

        assertEquals(1,validator.validate(request).size());

        bankcard.setIban(INCORRECT_IBAN);
        assertEquals(2,validator.validate(request).size());

        creditcard.setCowner(INCORRECT_OWNER);
        assertEquals(3,validator.validate(request).size());


        

    }

    
}
