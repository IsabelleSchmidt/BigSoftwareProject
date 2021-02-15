package de.hsrm.mi.swtpro.pflamoehus.db_test_product;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;

import de.hsrm.mi.swtpro.pflamoehus.product.tags.Tag;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(Lifecycle.PER_CLASS)
public class TagValueTests {

    @LocalServerPort
    private int port;

    private ValidatorFactory factory = Validation.buildDefaultValidatorFactory();;
    private Validator validator = factory.getValidator();

    private final String VALUE = "Holzoptik";
    private final String FALSE_VALUE = "17";

    @AfterAll
    public void close() {
        factory.close();
    }

    @Test
    @DisplayName("create tag with wrong values")
    public void wrong_validation() {
        Tag tag1 = new Tag();
        tag1.setValue(FALSE_VALUE);
        Set<ConstraintViolation<Tag>> violations = validator.validate(tag1);
        assertEquals(1,violations.size());

    }

    @Test
    @DisplayName("create new with correct values")
    public void create_tag() {
        Tag tag1 = new Tag();
        tag1.setValue(VALUE);

        assertTrue(tag1.toString().contains(VALUE), "The tag must contain this value");
        assertTrue(validator.validate(tag1).isEmpty(), "The value must have a length of  3 chars minimum");

    }

}
