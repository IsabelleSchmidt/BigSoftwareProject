package de.hsrm.mi.swtpro.pflamoehus.db_test_product.db_picture_tests;

import static org.assertj.core.api.Assertions.assertThat;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.springframework.boot.test.context.SpringBootTest;
import de.hsrm.mi.swtpro.pflamoehus.product.picture.Picture;

@SpringBootTest
public class PictureValueTests {

    private final String PATH = "/chairs/chair1.jpg";
    private final String PATH2 = "/chairs/chair1.2.jpeg";
    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();

    @AfterAll
    public void close() {
        factory.close();
    }

    @Test
    @DisplayName("create Picture with correct values")
    public void create_picture() {
        Picture pic = new Picture();

        pic.setPath(PATH);
        assertThat(pic.toString()).contains(PATH);
        assertThat(validator.validate(pic)).isEmpty();

        pic.setPath(PATH2);
        assertThat(pic.toString()).contains(PATH2);
        assertThat(validator.validate(pic)).isEmpty();

    }

    @Test
    @DisplayName("try to create Picture with wrong values")
    public void check_picture_validation() {
        Picture pic = new Picture();

        pic.setPath("");
        assertThat(validator.validate(pic)).isNotEmpty();
    }

}
