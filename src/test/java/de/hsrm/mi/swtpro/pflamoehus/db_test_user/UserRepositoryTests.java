package de.hsrm.mi.swtpro.pflamoehus.db_test_user;


import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.dao.DataIntegrityViolationException;

import de.hsrm.mi.swtpro.pflamoehus.user.Gender;
import de.hsrm.mi.swtpro.pflamoehus.user.User;
import de.hsrm.mi.swtpro.pflamoehus.user.UserRepository;
import de.hsrm.mi.swtpro.pflamoehus.user.paymentmethods.BankcardRepository;
import de.hsrm.mi.swtpro.pflamoehus.user.paymentmethods.CreditcardRepository;
import de.hsrm.mi.swtpro.pflamoehus.user.roles.RolesRepository;
import de.hsrm.mi.swtpro.pflamoehus.user.adress.AdressRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class UserRepositoryTests {

    private final String FIRSTNAME = "Olaf der Dritte";
    private final Gender GENDER = Gender.DIVERSE;
    private final String PASSWORD = "HaaaaaaaHA11!";
    private final String LASTNAME = "Schmidt";
    private final LocalDate BIRTHDAY = LocalDate.of(1999, 1, 1);
    private final String EMAIL = "hansolaf@hs-rm.de";

    @Autowired
    private UserRepository userRepo;
    @Autowired
    private CreditcardRepository creditRepo;
    @Autowired
    private BankcardRepository bankRepo;
    @Autowired
    private AdressRepository adressRepo;
    @Autowired
    private RolesRepository rolesRepo;
   
  

    private static final Logger logger = LoggerFactory.getLogger(UserRepositoryTests.class);

    @BeforeEach
    public void clear_repos() {
        
        
        creditRepo.deleteAll();
        bankRepo.deleteAll();
        adressRepo.deleteAll();
        userRepo.deleteAll();
        rolesRepo.deleteAll();
        
    }

    @Test
    void basiccheck() {
        assertThat(UserRepository.class).isInterface();
        assertThat(userRepo).isNotNull();
    }

    @Test
    @DisplayName("Persist product entity (empty table)")
    void persist_user() {
        userRepo.deleteAll();
        final User unmanaged = new User();

        unmanaged.setFirstName(FIRSTNAME);
        unmanaged.setLastName(LASTNAME);
        unmanaged.setEmail(EMAIL);
        unmanaged.setBirthdate(BIRTHDAY);
        unmanaged.setGender(GENDER);
        unmanaged.setPassword(PASSWORD);

        logger.info("REPO: " + userRepo.findAll());
        //System.out.println("REPO" + userRepo.findAll());
        List<User> repo = userRepo.findAll();
        final User managed = userRepo.save(unmanaged);
        assertThat(managed).isEqualTo(unmanaged);

        assertEquals(1, userRepo.count(), "REPO: " + repo);

    }

    @Test
    @DisplayName("different users cannot have the same E-mail adress")
    void product_name_unique() {
        final User user1 = new User();

        user1.setFirstName(FIRSTNAME);
        user1.setLastName(LASTNAME);
        user1.setEmail(EMAIL);
        user1.setBirthdate(BIRTHDAY);
        user1.setGender(GENDER);
        user1.setPassword(PASSWORD);

        userRepo.save(user1);

        final User user2 = new User();

        user2.setFirstName(FIRSTNAME + "olaf");
        user2.setLastName(LASTNAME + "gottfried");
        user2.setEmail(EMAIL);
        user2.setBirthdate(BIRTHDAY);
        user2.setGender(GENDER);
        user2.setPassword(PASSWORD + "passwort");

        Assertions.assertThrows(DataIntegrityViolationException.class, () -> {
            userRepo.save(user2);
        });

        assertThat(userRepo.count()).isEqualTo(1);

    }

    @Test
    @Transactional
    @DisplayName("UserRepo findByEmail und findById")
    void userRepo_findBy() {
        final int COUNT = 5;
        List<User> testUsers = new ArrayList<User>();

        for (int i = 0; i < COUNT; i++) {
            final User user1 = new User();
            testUsers.add(user1);
            user1.setFirstName(FIRSTNAME + i);
            user1.setLastName(LASTNAME + i);
            user1.setEmail(i + EMAIL);
            user1.setBirthdate(BIRTHDAY);
            user1.setGender(GENDER);
            user1.setPassword(PASSWORD + i);

            userRepo.save(user1);
        }

        assertThat(userRepo.count()).isEqualTo(COUNT);

        for (int i = 0; i < COUNT; i++) {
            Optional<User> fund = userRepo.findByEmail(i + EMAIL);
            User user_i = testUsers.get(i);
            if (fund.isPresent()) {
                assertThat(fund.get().toString()).contains(user_i.getPassword()).contains(user_i.getFirstName())
                        .contains(user_i.getLastName()).contains(user_i.getEmail())
                        .contains(Long.toString(user_i.getId()));
            }

            fund = userRepo.findById(testUsers.get(i).getId());
            if (fund.isPresent()) {
                assertThat(fund.get().toString()).contains(user_i.getPassword()).contains(user_i.getFirstName())
                        .contains(user_i.getLastName()).contains(user_i.getEmail())
                        .contains(Long.toString(user_i.getId()));
            }
        }

    }

    @Test
    @DisplayName("UserRepo delete")
    void userRepo_delete() {
        final int COUNT = 5;
        List<User> testUsers = new ArrayList<User>();

        for (int i = 0; i < COUNT; i++) {
            final User user1 = new User();
            testUsers.add(user1);
            user1.setFirstName(FIRSTNAME + i);
            user1.setLastName(LASTNAME + i);
            user1.setEmail(i + EMAIL);
            user1.setBirthdate(BIRTHDAY);
            user1.setGender(GENDER);
            user1.setPassword(PASSWORD + i);

            userRepo.save(user1);

        }
        assertThat(userRepo.count()).isEqualTo(COUNT);

        for (int i = 0; i < COUNT; i++) {
            User user_i = testUsers.get(i);
            userRepo.delete(user_i);
            assertThat(userRepo.count()).isEqualTo(COUNT - (i + 1));

        }
    }

}
