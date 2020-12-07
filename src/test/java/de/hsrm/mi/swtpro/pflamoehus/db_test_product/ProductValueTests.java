package de.hsrm.mi.swtpro.pflamoehus.db_test_product;

import static org.junit.jupiter.api.Assertions.assertTrue;


import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;

import de.hsrm.mi.swtpro.pflamoehus.product.Product;


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(Lifecycle.PER_CLASS)
public class ProductValueTests {
    
    @LocalServerPort
    private int port;

    private Validator validator;
    private ValidatorFactory factory;

    private final String NAME = "Hanspeter";
    private final double PRICE = 2.5;
    private final double WIDTH = 10.0;
    private final double HEIGHT = 12.0;
    private final double DEPTH = 0.0;
    private final String PRODUCTTYPE = "Tisch";
    private final String ROOMTYPE = "Wohnzimmer";
    private final String PICTURE = "src\\main\\resources\\static\\tables\\table1.jpg";
    private final int AVAILABLE = 0;
    private final double FALSE_PRICE =-1;
    private final double FALSE_WIDTH = 12.123;
    private final double FALSE_HEIGHT = 12345678;
    private final String FALSE_PICTURE = "a.gif";
    private final String FALSE_PRODUCTTYPE = "Ziharmonika";
    private final String FALSE_ROOMTYPE ="SCHlafzimmer";
    private final int FALSE_AVAILABLE = -17;
    private final double FALSE_PRICE2 = 123.123;


    @BeforeAll
    public void createValidator(){
        factory =  Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @AfterAll
    public void close(){
        factory.close();
    }

    @Test 
    @DisplayName("New Product with wrong validation properties")
    public void false_validation(){
        
        Product product1 = new Product();
        int nrWrongValues = 7;

        //Product 1
        //fill with wrong values
        product1.setHeight(FALSE_HEIGHT);
        product1.setWidth(FALSE_WIDTH);
        product1.setPicture(FALSE_PICTURE);
        product1.setNrAvailableItems(FALSE_AVAILABLE);
        product1.setPrice(FALSE_PRICE);
        product1.setRoomType(FALSE_ROOMTYPE);
        product1.setProductType(FALSE_PRODUCTTYPE);

        //right values
        product1.setName(NAME);
        product1.setDepth(DEPTH);

        //check for as many validation errors as wrong values
        Set<ConstraintViolation<Product>> violations;
        violations = validator.validate(product1);
        assertTrue(violations.size() == nrWrongValues,"The number of false values needs to be "+nrWrongValues);
        

        product1.setPrice(2.0);
        violations = validator.validate(product1);
        assertTrue(violations.size()==nrWrongValues-1,"The price needs to ne validated now.");

        product1.setPrice(FALSE_PRICE2);
        violations = validator.validate(product1);
        assertTrue(violations.size()==nrWrongValues, "The number of correctly detected violations is "+nrWrongValues);
    }


    @Test 
    @DisplayName("New Product with correct values")
    public void product_data(){
        Product product = new Product();
        product.setName(NAME);
        product.setDepth(DEPTH);
        product.setHeight(HEIGHT);
        product.setWidth(WIDTH);
        product.setPrice(PRICE);
        product.setNrAvailableItems(AVAILABLE);
        product.setProductType(PRODUCTTYPE);
        product.setRoomType(ROOMTYPE);
        product.setPicture(PICTURE);

        String productvalues = product.toString();

        assertTrue(productvalues.contains(NAME),"The product needs to contain the correct name.");
        assertTrue(productvalues.contains(PRODUCTTYPE),"The product needs to contain the correct producttype.");
        assertTrue(productvalues.contains(ROOMTYPE), "The product needs to contain the correct roomtype.");
        assertTrue(productvalues.contains(PICTURE),"The product needs to contain a picture.");
        assertTrue(productvalues.contains(String.valueOf(DEPTH)),"The product needs to contain the correct depth.");
        assertTrue(productvalues.contains(String.valueOf(HEIGHT)),"the product needs to contain the correct height.");
        assertTrue(productvalues.contains(String.valueOf(WIDTH)),"the product needs to contain the correct width.");
        assertTrue(productvalues.contains(String.valueOf(PRICE)),"the product needs to contain the correct price.");
        assertTrue(productvalues.contains(String.valueOf(AVAILABLE)),"the product needs to contain the correct number of available items.");

        assertTrue(validator.validate(product).isEmpty(),"The number of validation errors should be 0.");
        
    }

    


  
}
