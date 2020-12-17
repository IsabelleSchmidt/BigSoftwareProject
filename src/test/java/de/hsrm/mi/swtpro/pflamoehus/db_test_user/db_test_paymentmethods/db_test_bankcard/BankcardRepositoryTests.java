package de.hsrm.mi.swtpro.pflamoehus.db_test_user.db_test_paymentmethods.db_test_bankcard;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import de.hsrm.mi.swtpro.pflamoehus.user.paymentmethods.Bankcard;
import de.hsrm.mi.swtpro.pflamoehus.user.paymentmethods.BankcardRepository;

import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(Lifecycle.PER_CLASS)
@RunWith(SpringRunner.class)
public class BankcardRepositoryTests {

    @LocalServerPort
    private int port;

    @Autowired
    private BankcardRepository bankcardRepo;

    public final String IBAN = "DE89 3704 0044 0532 0130 00";
    public final String OWNER = "Steven Bob";
    public final String BANK = "Sparkasse";

    @Test
    public void basecheck() {
        assertThat(BankcardRepository.class).isInterface();
        assertThat(bankcardRepo).isNotNull();
    }

    @Test
    @DisplayName("Persist bankcard entity (empty table)")
    public void bankcard_persist() {
        bankcardRepo.deleteAll();

        final Bankcard unmanaged = new Bankcard();
        unmanaged.setIban(IBAN);
        unmanaged.setOwner(OWNER);
        unmanaged.setBank(BANK);

        bankcardRepo.deleteAll();
        final Bankcard managed = bankcardRepo.save(unmanaged);
        assertThat(managed).isEqualTo(unmanaged);

        assertThat(bankcardRepo.count()).isEqualTo(1);

    }

    @Test
    @DisplayName("Save and delete adresses from repository")
    public void save_and_delete_adress() {

        bankcardRepo.deleteAll();

        Bankcard bc = new Bankcard();
        bc.setIban(IBAN);
        bc.setOwner(OWNER);
        bc.setBank(BANK);
        bankcardRepo.save(bc);
        assertEquals(bankcardRepo.count(),1);

        bankcardRepo.delete(bc);
        assertEquals(bankcardRepo.count(),0);

    }

    @Test
    @DisplayName("ProductRepository findById")
    public void findById() {

        bankcardRepo.deleteAll();

        final Bankcard bc = new Bankcard();
        bc.setIban(IBAN);
        bc.setOwner(OWNER);
        bc.setBank(BANK);
        bankcardRepo.save(bc);

        assertThat(bankcardRepo.count()).isEqualTo(1);

        Optional<Bankcard> bc1 = bankcardRepo.findById(bc.getId());
        assertThat(bc1.get().getOwner()).isEqualTo(OWNER);
        assertThat(bc1.get().getIban()).isEqualTo(IBAN);
        
    }
    
}
