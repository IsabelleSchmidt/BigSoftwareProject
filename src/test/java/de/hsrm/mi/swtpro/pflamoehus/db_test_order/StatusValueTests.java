package de.hsrm.mi.swtpro.pflamoehus.db_test_order;

import javax.validation.ValidatorFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import javax.validation.Validation;
import javax.validation.Validator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import de.hsrm.mi.swtpro.pflamoehus.order.status.Status;
import de.hsrm.mi.swtpro.pflamoehus.order.status.Statuscode;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
public class StatusValueTests {
    private ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private Validator validator = factory.getValidator();

    @AfterAll
    public void init(){
        factory.close();
    }

    @Test
    @DisplayName("fill statuscode with correct values")
    public void statuscode_correct(){
        Status status = new Status();

        for(Statuscode aktcode :    Statuscode.values()){
            status.setStatuscode(aktcode);
            assertEquals(0,validator.validate(status).size());
        }   
         
    }

  
}
