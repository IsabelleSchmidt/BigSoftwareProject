package de.hsrm.mi.swtpro.pflamoehus.db_test_email;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.web.servlet.MockMvc;
import javax.transaction.Transactional;

import de.hsrm.mi.swtpro.pflamoehus.email.PasswordRequest;
import de.hsrm.mi.swtpro.pflamoehus.email.PasswordRequestRepository;
import de.hsrm.mi.swtpro.pflamoehus.email.emailapi.EmailRestApi;
import de.hsrm.mi.swtpro.pflamoehus.email.emailservice.EmailService;
import de.hsrm.mi.swtpro.pflamoehus.email.passwordrequestservice.PasswordRequestService;
import de.hsrm.mi.swtpro.pflamoehus.exceptions.service.EmailServiceException;

@SpringBootTest
@RunWith(SpringRunner.class)
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

    private final String EMAIL_EXISTING = "user@pflamoehus.de";
    private final String EMAIL_NOTEXISTING = "userxxx@pflamooooehus.de";

    @Test
    @DisplayName("findBy email")
    public void findBy(){

        pwreqservice.saveNewRequest(EMAIL_EXISTING);

        Optional<PasswordRequest> exists = pwreqrepo.findByEmail(EMAIL_EXISTING);

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Long milliseconds = timestamp.getTime();

        assertTrue(exists.isPresent());
        assertThat(exists.get().getTimestamp() < milliseconds);

    }
    
}
