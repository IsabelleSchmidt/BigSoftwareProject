package de.hsrm.mi.swtpro.pflamoehus.db_test_order;

import java.time.LocalDate;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertTrue;

import de.hsrm.mi.swtpro.pflamoehus.order.Order;

@SpringBootTest
@RunWith(SpringRunner.class)
public class OrderValueTests {
    
    private ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private Validator validator = factory.getValidator();

    @AfterAll
    public void close(){
        factory.close();
    }

    @Test
    @DisplayName("fill deliverydate with correct values")
    public void delivery_date_correct(){
        Order order = new Order();

        LocalDate delivery = LocalDate.parse("2021-02-15");
        order.setDeliveryDate(delivery);
        assertTrue(validator.validate(order).size() == 0);

        delivery = LocalDate.now();
        order.setDeliveryDate(delivery);
        assertTrue(validator.validate(order).size() == 0);

         
    }

    @Test
    @DisplayName("fill delivery_date with wrong values")
    public void delivery_date_wrong(){
        Order order = new Order();

        LocalDate delivery = LocalDate.parse("2020-02-15");
        order.setDeliveryDate(delivery);
        assertTrue(validator.validate(order).size() == 1);
        
    }


}
