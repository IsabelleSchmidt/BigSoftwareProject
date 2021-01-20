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


import static org.junit.jupiter.api.Assertions.assertEquals;

import de.hsrm.mi.swtpro.pflamoehus.order.Order;

@SpringBootTest
@RunWith(SpringRunner.class)
public class OrderValueTests {
    
    private ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private Validator validator = factory.getValidator();
    private final LocalDate DELIVERY = LocalDate.now().plusDays(1);
    private final String EMAIL = "abdcgsdh@sbsb.de";
    private final double PRICE = 10;

    @AfterAll
    public void close(){
        factory.close();
    }

    @Test
    @DisplayName("fill deliverydate with correct values")
    public void delivery_date_correct(){
        Order order = new Order();

       
        order.setDeliveryDate(DELIVERY);
        order.setCustomerEmail(EMAIL);
        order.setPriceTotal(PRICE);
        assertEquals(validator.validate(order).size(), 0);

        LocalDate delivery = LocalDate.now();
        order.setDeliveryDate(delivery);
        assertEquals(validator.validate(order).size(), 0);

         
    }

    @Test
    @DisplayName("fill delivery_date with wrong values")
    public void delivery_date_wrong(){
        Order order = new Order();

        LocalDate delivery = LocalDate.parse("2020-02-15");
        order.setCustomerEmail(EMAIL);
        order.setDeliveryDate(delivery);
        order.setPriceTotal(PRICE);
        assertEquals(validator.validate(order).size(), 1);

        
    }


}
