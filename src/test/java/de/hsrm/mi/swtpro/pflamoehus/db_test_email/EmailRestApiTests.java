package de.hsrm.mi.swtpro.pflamoehus.db_test_email;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.UnsupportedEncodingException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import javax.transaction.Transactional;
import de.hsrm.mi.swtpro.pflamoehus.email.PasswordRequestRepository;
import de.hsrm.mi.swtpro.pflamoehus.email.emailapi.EmailRestApi;
import de.hsrm.mi.swtpro.pflamoehus.email.emailservice.EmailService;
import de.hsrm.mi.swtpro.pflamoehus.email.passwordrequestservice.PasswordRequestService;
import de.hsrm.mi.swtpro.pflamoehus.exceptions.service.EmailServiceException;


@SpringBootTest(webEnvironment =WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class EmailRestApiTests {

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
    MockMvc mockmvc;

    private final String EMAIL_EXISTING = "user@pflamoehus.de";
    private final String EMAIL_NOTEXISTING = "userxxx@pflamooooehus.de";

    @AfterEach
    public void clearRepos(){
        pwreqrepo.deleteAll();
    }

    @Test
    public void basecheck() {
        assertThat(pwreqrepo).isNotNull();
        assertThat(pwreqrepo).isNotNull();
    }

    @Test
    @Transactional
    @DisplayName("/api/email/send should save the email with a random string and the current timestamp")
    public void postNewOrder_newemail() throws Exception {

        pwreqrepo.deleteAllInBatch();
        assertThat(pwreqrepo.count()).isEqualTo(0);

        System.out.print("EMAIL /api/email/send: " + mockmvc
                .perform(post("/api/email/send").contentType(MediaType.APPLICATION_JSON_VALUE).content(EMAIL_EXISTING))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString());

        assertThat(pwreqrepo.count()).isEqualTo(1);

    }

    @Test
    @Transactional
    @DisplayName("/api/email/getcode/{email} should get the code to an existing email")
    public void getCodeFromPRP_existingemail() throws UnsupportedEncodingException, Exception {

        final int MINIMALCODELENGTH = 23; 

        pwreqrepo.deleteAllInBatch();
        assertThat(pwreqrepo.count()).isEqualTo(0);

        System.out.print("EMAIL /api/email/send: "+mockmvc.perform(post("/api/email/send").contentType(MediaType.APPLICATION_JSON_VALUE).content(EMAIL_EXISTING))
        .andExpect(status().isOk())
        .andReturn().getResponse().getContentAsString());

        assertThat(emailcontroller.getCode(EMAIL_EXISTING).length() > MINIMALCODELENGTH);
        assertThat(emailcontroller.getCode(EMAIL_EXISTING).split("$").length == 2);

    }

    @Test
    @Transactional
    @DisplayName("/api/email/getcode/{email} should get an exception when the email does not exists")
    public void getCodeFromPRP_notexistingemail() throws UnsupportedEncodingException, Exception {

        boolean thrown = false;
        try {
            emailcontroller.getCode(EMAIL_NOTEXISTING);
        } catch(EmailServiceException ese) {
            thrown = true;
        }
        assertTrue(thrown); 

    }


    

    



}
