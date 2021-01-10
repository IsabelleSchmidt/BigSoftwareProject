package de.hsrm.mi.swtpro.pflamoehus.db_test_user.db_test_roles;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.springframework.boot.test.context.SpringBootTest;

import de.hsrm.mi.swtpro.pflamoehus.roles.ERoles;
import de.hsrm.mi.swtpro.pflamoehus.roles.Roles;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class RolesValueTests {
    
    private final ERoles role = ERoles.USER;

    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();

    @AfterAll
    public void close() {
        factory.close();
    }

    @Test
    @DisplayName("create Role with correct values")
    public void create_role() {
        Roles role1 = new Roles();

        role1.setName(role);

        assertThat(role1.toString()).contains(role.toString());
        assertThat(validator.validate(role)).isEmpty();
    }

}
