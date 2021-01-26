package de.hsrm.mi.swtpro.pflamoehus.db_test_product;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.assertj.core.api.Assertions.assertThat;
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
import de.hsrm.mi.swtpro.pflamoehus.product.ProductType;
import de.hsrm.mi.swtpro.pflamoehus.product.RoomType;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(Lifecycle.PER_CLASS)
class ProductValueTests {

    @LocalServerPort
    private int port;

    private Validator validator;
    private ValidatorFactory factory;

    private final String NAME = "Hanspeter";
    private final double PRICE = 2.5;
    private final double WIDTH = 10.0;
    private final double HEIGHT = 12.0;
    private final double DEPTH = 0.0;
    private final ProductType PRODUCTTYPE = ProductType.TABLE;
    private final RoomType ROOMTYPE = RoomType.LIVINGROOM;
    private final int AVAILABLE = 0;
    private final String DESCRIPTION = "Beschreibung ist da.";
    private final String INFORMATION = "Information ist da.";
    private final double FALSE_PRICE = -1;
    private final double FALSE_WIDTH = 12.123;
    private final double FALSE_HEIGHT = 12345678;
    private final int FALSE_AVAILABLE = -17;
    private final double FALSE_PRICE2 = 123.123;
    private final String FALSE_DESCRIPTION = "nein";
    private final String FALSE_INFORMATION = "nein";

    @BeforeAll
    void createValidator() {
        factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @AfterAll
    void close() {
        factory.close();
    }

    @Test
    @DisplayName("New Product with wrong validation properties")
    void false_validation() {

        Product product1 = new Product();
        int nrWrongValues = 6;

        // Product 1
        // fill with wrong values
        product1.setHeight(FALSE_HEIGHT);
        product1.setWidth(FALSE_WIDTH);
        product1.setAvailable(FALSE_AVAILABLE);
        product1.setPrice(FALSE_PRICE);
        product1.setDescription(FALSE_DESCRIPTION);
        product1.setInformation(FALSE_INFORMATION);

        // right values
        product1.setName(NAME);
        product1.setDepth(DEPTH);
        product1.setRoomType(ROOMTYPE);
        product1.setProductType(PRODUCTTYPE);

        // check for as many validation errors as wrong values
        Set<ConstraintViolation<Product>> violations;
        violations = validator.validate(product1);
        assertEquals(violations.size(),nrWrongValues);

        product1.setPrice(2.0);
        violations = validator.validate(product1);
        assertEquals(violations.size(),nrWrongValues - 1);

        product1.setPrice(FALSE_PRICE2);
        violations = validator.validate(product1);
        assertEquals(violations.size(),nrWrongValues);
    }

    @Test
    @DisplayName("New Product with correct values")
    void product_data() {
        Product product = new Product();
        product.setName(NAME);
        product.setDepth(DEPTH);
        product.setHeight(HEIGHT);
        product.setWidth(WIDTH);
        product.setPrice(PRICE);
        product.setAvailable(AVAILABLE);
        product.setProductType(PRODUCTTYPE);
        product.setRoomType(ROOMTYPE);
        product.setDescription(DESCRIPTION);
        product.setInformation(INFORMATION);

        assertThat(product.toString()).contains(NAME).contains(PRODUCTTYPE.toString()).contains(ROOMTYPE.toString()).contains(DESCRIPTION)
                .contains(String.valueOf(DEPTH)).contains(String.valueOf(WIDTH)).contains(String.valueOf(HEIGHT))
                .contains(String.valueOf(PRICE)).contains(String.valueOf(AVAILABLE)).contains(INFORMATION);

        assertTrue(validator.validate(product).isEmpty(), "The number of validation errors should be 0.");

    }

}
