package de.hsrm.mi.swtpro.pflamoehus.db_test_user.db_test_roles;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import de.hsrm.mi.swtpro.pflamoehus.roles.ERoles;
import de.hsrm.mi.swtpro.pflamoehus.roles.Roles;
import de.hsrm.mi.swtpro.pflamoehus.roles.RolesRepository;

import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(Lifecycle.PER_CLASS)
@RunWith(SpringRunner.class)
public class RolesRepositoryTests {
    
    @LocalServerPort
    private int port;

    @Autowired
    private RolesRepository rolesRepo;

    private final ERoles role = ERoles.USER;
    
    @Test
    public void basecheck() {
        assertThat(RolesRepository.class).isInterface();
        assertThat(rolesRepo).isNotNull();
    }

    @Test
    @DisplayName("Persist roles entity (empty table)")
    public void product_persist() {
        rolesRepo.deleteAll();

        final Roles unmanaged = new Roles();
        unmanaged.setName(role);

        final Roles managed = rolesRepo.save(unmanaged);
        assertThat(managed).isEqualTo(unmanaged);

        assertThat(rolesRepo.count()).isEqualTo(1);

    }

    @Test
    @DisplayName("Save and delete roles from repository")
    public void save_and_delete_adress() {

        rolesRepo.deleteAll();

        Roles role1 = new Roles();
        role1.setName(role);
        rolesRepo.save(role1);

        rolesRepo.delete(role1);
        assertEquals(0,rolesRepo.count());

    }

    @Test
    @DisplayName("ProductRepository findByName")
    public void findByName() {

        rolesRepo.deleteAll();

        final Roles role1 = new Roles();
        role1.setName(role);
        rolesRepo.save(role1);

        assertThat(rolesRepo.count()).isEqualTo(1);

        Roles role2 = rolesRepo.findByName(role).get();
        assertThat(role2.getName()).isEqualTo(role);

    }

    
}
