package de.hsrm.mi.swtpro.pflamoehus.db_test_order;

import javax.validation.ValidatorFactory;

import static org.junit.jupiter.api.Assertions.assertTrue;
import javax.validation.Validation;
import javax.validation.Validator;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import de.hsrm.mi.swtpro.pflamoehus.order.Status;
import de.hsrm.mi.swtpro.pflamoehus.order.Statuscode;

@SpringBootTest
@RunWith(SpringRunner.class)
public class StatusValueTests {
    //Testen dass der richtige Status reingeschrieben wird in value
    //Testen ob man falsche orderid etc mitgeben kann
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
            status.setStatuscode(aktcode.toString());
            assertTrue(validator.validate(status).size() == 0, "Statuscode muss als korrekt validiert werden");
        }   
         
    }

    @Test
    @DisplayName("fill statuscode with wrong values")
    public void statuscode_wrong(){
        Status status = new Status();

        for(Statuscode aktcode :    Statuscode.values()){
            status.setStatuscode(aktcode.toString()+"s");
            assertTrue(validator.validate(status).size() == 0, "Statuscode mit s hintendran muss als falsch validiert werden");
        }   

        for(Statuscode aktcode :    Statuscode.values()){
            status.setStatuscode(aktcode.toString().toLowerCase());
            assertTrue(validator.validate(status).size() == 0, "Statuscode in klein muss als falsch validiert werden");
        }   

      
        status.setStatuscode("");
        assertTrue(validator.validate(status).size() == 0, "Statuscode '' muss als falsch validiert werden");
        
    }
}
