package de.hsrm.mi.swtpro.pflamoehus.db_test_jwtstore;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import de.hsrm.mi.swtpro.pflamoehus.security.jwt.JwtStore.JwtStore;
import de.hsrm.mi.swtpro.pflamoehus.security.jwt.JwtStore.JwtStoreRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class JwtStoreRepositoryTests {

    private final String ACCESSTOKEN = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyQHBmbGFtb2VodXMuZGUifQ.M9Gzcu_USHv8Z_zSztsuT_0jveWVsuWd5Zd6OOd7biAmHwz_ZmuvLKBwsqXMuJb8Jh2R8DOd7id4KovDIwXKFg";
    
    @Autowired
    private JwtStoreRepository jwtStoreRepo;

    @Test
    void basecheck(){
        assertThat(JwtStoreRepository.class).isInterface();
        assertThat(jwtStoreRepo).isNotNull();
    }

    @Test
    @DisplayName("Persist jwtStore entity (empty table)")
    void persist_jwtStore(){
        jwtStoreRepo.deleteAll();
        final JwtStore unmanaged = new JwtStore();

        unmanaged.setToken(ACCESSTOKEN);

        final JwtStore managed = jwtStoreRepo.save(unmanaged);
        assertThat(managed).isEqualTo(unmanaged);
        assertEquals(1, jwtStoreRepo.count());
    }

    @Test
    @DisplayName("Exists by token.")
    void existsByToken (){

        jwtStoreRepo.deleteAll();

        final int COUNT = 5;

        for (int i = 0; i < COUNT; i++) {
            JwtStore store = new JwtStore();
            store.setToken(ACCESSTOKEN + i);

            jwtStoreRepo.save(store);
        }

        assertThat(jwtStoreRepo.count()).isEqualTo(COUNT);

        for (int i = 0; i < COUNT; i++) {
            assertTrue(jwtStoreRepo.existsByToken(ACCESSTOKEN + i));
            }
    }

    

    @Test
    @DisplayName("Find by id")
    void findById(){

        jwtStoreRepo.deleteAll();

        final int COUNT = 5;

        for (int i = 0; i < COUNT; i++) {
            JwtStore store = new JwtStore();
            store.setToken(ACCESSTOKEN + i);

            jwtStoreRepo.save(store);
        }

        assertThat(jwtStoreRepo.count()).isEqualTo(COUNT);

        for (int i = 0; i < COUNT; i++) {
            long id = 1;
            Optional<JwtStore> fund = jwtStoreRepo.findById(id);
            if(fund.isPresent()){
                assertThat(fund.get().toString().contains(ACCESSTOKEN+i));
            }

        }

    }

    @Test
    @DisplayName("Find by token")
    void findByToken(){

        jwtStoreRepo.deleteAll();

        final int COUNT = 5;

        for (int i = 0; i < COUNT; i++) {
            JwtStore store = new JwtStore();
            store.setToken(ACCESSTOKEN + i);

            jwtStoreRepo.save(store);
        }

        assertThat(jwtStoreRepo.count()).isEqualTo(COUNT);

        for (int i = 0; i < COUNT; i++) {
            List<JwtStore> fund = jwtStoreRepo.findByToken(ACCESSTOKEN + i);
            assertTrue(!fund.isEmpty());

        }

    }

}
