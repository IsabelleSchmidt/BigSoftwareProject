package de.hsrm.mi.swtpro.pflamoehus.db_test_email;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.DisplayName;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Timestamp;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;


import de.hsrm.mi.swtpro.pflamoehus.email.PasswordRequest;
import de.hsrm.mi.swtpro.pflamoehus.email.PasswordRequestRepository;
import de.hsrm.mi.swtpro.pflamoehus.email.emailapi.EmailRestApi;
import de.hsrm.mi.swtpro.pflamoehus.email.emailservice.EmailService;
import de.hsrm.mi.swtpro.pflamoehus.email.passwordrequestservice.PasswordRequestService;
import de.hsrm.mi.swtpro.pflamoehus.user.UserRepository;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class PasswordRequestRepoTests {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    PasswordRequestRepository pwreqrepo;

    @Autowired
    EmailService emailservice;

    @Autowired
    PasswordRequestService pwreqservice;

    @Autowired
    EmailRestApi emailcontroller;

    @Autowired
    UserRepository userRepo;

    private final String EMAIL_EXISTING = "user@pflamoehus.de";

    @Test
    @DisplayName("findBy email")
    public void findBy(){

        pwreqrepo.deleteAllInBatch();

        pwreqservice.saveNewRequest(EMAIL_EXISTING);

        assertEquals(1, pwreqrepo.count());

        Optional<PasswordRequest> exists = pwreqrepo.findByEmail(EMAIL_EXISTING);

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Long milliseconds = timestamp.getTime();

        assertTrue(exists.isPresent());
        assertThat(exists.get().getTimestamp() < milliseconds);

    }
    
}
