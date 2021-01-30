package de.hsrm.mi.swtpro.pflamoehus.email.emailapi;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import de.hsrm.mi.swtpro.pflamoehus.email.emailservice.EmailService;

/*
 * EmailRestController for the communcation between front- and backend.
 * 
 * @author Sarah Wenzel
 * @version 1
 */
@RestController
@CrossOrigin
@RequestMapping("/api/email")
public class EmailRestApi {

    @Autowired
    EmailService emailservice;

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailRestApi.class);

    @PostMapping("/send")
    public boolean sendEmail(@RequestBody String email) {
        String topic = "Passwort zurücksetzen im Pflamoehus!";
        String link = "http://localhost:8080/resetPassword/" + email.replace("\"", "");
        String text = "Guten Tag! über folgenden Link kannst du dein Passwort zurücksetzen: " + link;

        try {
            emailservice.sendEmail(email, text, topic);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
