package de.hsrm.mi.swtpro.pflamoehus.db_test_order;

import java.time.LocalDate;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import static org.junit.jupiter.api.Assertions.assertEquals;

import de.hsrm.mi.swtpro.pflamoehus.order.Order;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
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
        assertEquals(0,validator.validate(order).size());

        LocalDate delivery = LocalDate.now();
        order.setDeliveryDate(delivery);
        assertEquals(0, validator.validate(order).size());

         
    }

    @Test
    @DisplayName("fill delivery_date with wrong values")
    public void delivery_date_wrong(){
        Order order = new Order();

        LocalDate delivery = LocalDate.parse("2020-02-15");
        order.setCustomerEmail(EMAIL);
        order.setDeliveryDate(delivery);
        order.setPriceTotal(PRICE);
        assertEquals(1, validator.validate(order).size());

        
    }


}
