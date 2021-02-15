package de.hsrm.mi.swtpro.pflamoehus.db_test_user.db_test_paymentmethods.db_test_creditcard;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import de.hsrm.mi.swtpro.pflamoehus.user.UserRepository;
import de.hsrm.mi.swtpro.pflamoehus.user.paymentmethods.Creditcard;
import de.hsrm.mi.swtpro.pflamoehus.user.paymentmethods.CreditcardRepository;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class CreditcardRepositoryTests {

    //save, delete, findby
    @Autowired UserRepository userRepo;
    @Autowired CreditcardRepository creditRepo;

    final private String OWNER = "Christopf";
    final private String CREDITCARDNUMBER = "4111111111111";
    final private LocalDate EXPIRY = LocalDate.of(2023,10,04);

    @BeforeEach
    public void clear_repos(){

        creditRepo.deleteAll();
        userRepo.deleteAll();

    }

    @Test
    @DisplayName("save Creditcard in Repository")
    public void save_card(){
        Creditcard unmanaged = new Creditcard();
        unmanaged.setCreditcardnumber(CREDITCARDNUMBER);
        unmanaged.setDateOfExpiry(EXPIRY);
        unmanaged.setCowner(OWNER);

        creditRepo.deleteAll();
        final Creditcard managed = creditRepo.save(unmanaged);
        assertThat(managed).isEqualTo(unmanaged);
        assertThat(creditRepo.count()).isEqualTo(1);

    }

    @Test
    @DisplayName("Delete Creditcard in Repository")
    public void delete_card(){
        creditRepo.deleteAll();
        List<Creditcard> allcards = new ArrayList<Creditcard>();

        final int COUNT = 5;
        for(int i = 0; i<COUNT;i++){
            Creditcard card = new Creditcard();
            card.setCreditcardnumber(CREDITCARDNUMBER);
            card.setCowner(OWNER+i);
            card.setDateOfExpiry(EXPIRY);
            allcards.add(card);
            creditRepo.save(card);
           
        } 
        assertEquals(creditRepo.count(),COUNT);

        for(int i = COUNT;i>0;i--){
            creditRepo.delete(allcards.get((i-1)));
            assertEquals(creditRepo.count(),i-1);
        }
    }

    @Test
    @DisplayName("CreditcardRepository findBy")
    public void creditcard_findby(){

        //findby id und expiry
        Creditcard unmanaged = new Creditcard();
        unmanaged.setCreditcardnumber(CREDITCARDNUMBER);
        unmanaged.setDateOfExpiry(EXPIRY);
        unmanaged.setCowner(OWNER);

        creditRepo.save(unmanaged);
       Creditcard found = creditRepo.findById(unmanaged.getId()).get();
        assertThat(found.toString())
        .contains(Long.toString(found.getId()))
        .contains(unmanaged.getCowner());

        assertThat(creditRepo.findByDateOfExpiry(unmanaged.getDateOfExpiry()).size()).isEqualTo(1);


    }
}
