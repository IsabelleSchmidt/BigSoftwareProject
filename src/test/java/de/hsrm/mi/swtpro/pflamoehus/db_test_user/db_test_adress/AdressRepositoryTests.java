package de.hsrm.mi.swtpro.pflamoehus.db_test_user.db_test_adress;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import de.hsrm.mi.swtpro.pflamoehus.user.adress.Adress;
import de.hsrm.mi.swtpro.pflamoehus.user.adress.AdressRepository;

import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Optional;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(SpringExtension.class)
public class AdressRepositoryTests {

    @LocalServerPort
    private int port;

    @Autowired
    private AdressRepository adressRepo;

    private final String STREETNAME = "Erlenstraße";
    private final String HOUSENUMBER = "15a";
    private final String POSTCODE = "55433";
    private final String CITY = "Wiesbaden";

    @Test
    public void basecheck() {
        assertThat(AdressRepository.class).isInterface();
        assertThat(adressRepo).isNotNull();
    }

    @Test
    @DisplayName("Persist adress entity (empty table)")
    public void product_persist() {
        adressRepo.deleteAll();

        final Adress unmanaged = new Adress();
        unmanaged.setCity(CITY);
        unmanaged.setHouseNumber(HOUSENUMBER);
        unmanaged.setStreetName(STREETNAME);
        unmanaged.setPostCode(POSTCODE);

        adressRepo.deleteAll();
        final Adress managed = adressRepo.save(unmanaged);
        assertThat(managed).isEqualTo(unmanaged);

        assertThat(adressRepo.count()).isEqualTo(1);

    }

    @Test
    @DisplayName("Save and delete adresses from repository")
    public void save_and_delete_adress() {

        adressRepo.deleteAll();

        Adress adress1 = new Adress();
        adress1.setCity(CITY);
        adress1.setHouseNumber(HOUSENUMBER);
        adress1.setPostCode(POSTCODE);
        adress1.setStreetName(STREETNAME);
        adressRepo.save(adress1);
        assertEquals(1,adressRepo.count());

        adressRepo.delete(adress1);
        assertEquals(0,adressRepo.count());

    }

    @Test
    @DisplayName("ProductRepository findByCity")
    public void findByCity() {

        adressRepo.deleteAll();

        final Adress adress = new Adress();
        adress.setCity(CITY);
        adress.setHouseNumber(HOUSENUMBER);
        adress.setPostCode(POSTCODE);
        adress.setStreetName(STREETNAME);
        adressRepo.save(adress);

        assertThat(adressRepo.count()).isEqualTo(1);

        List<Adress> adress1 = adressRepo.findByCity(CITY);
        assertThat(adress1.get(0).getHouseNumber()).isEqualTo(HOUSENUMBER);
        assertThat(adress1.get(0).getPostCode()).isEqualTo(POSTCODE);

    }

    @Test
    @DisplayName("ProductRepository findByPostcode")
    public void findByPostcode() {

        adressRepo.deleteAll();

        final Adress adress = new Adress();
        adress.setCity(CITY);
        adress.setHouseNumber(HOUSENUMBER);
        adress.setPostCode(POSTCODE);
        adress.setStreetName(STREETNAME);
        adressRepo.save(adress);

        assertThat(adressRepo.count()).isEqualTo(1);

        List<Adress> adress1 = adressRepo.findByPostCode(POSTCODE);
        assertThat(adress1.get(0).getHouseNumber()).isEqualTo(HOUSENUMBER);
        assertThat(adress1.get(0).getCity()).isEqualTo(CITY);

    }

    @Test
    @DisplayName("ProductRepository findById")
    public void findById() {

        adressRepo.deleteAll();

        final Adress adress = new Adress();
        adress.setCity(CITY);
        adress.setHouseNumber(HOUSENUMBER);
        adress.setPostCode(POSTCODE);
        adress.setStreetName(STREETNAME);
        adressRepo.save(adress);

        assertThat(adressRepo.count()).isEqualTo(1);

        Optional<Adress> adress1 = adressRepo.findById(adress.getId());
        assertThat(adress1.get().getHouseNumber()).isEqualTo(HOUSENUMBER);
        assertThat(adress1.get().getCity()).isEqualTo(CITY);
        
    }

}
