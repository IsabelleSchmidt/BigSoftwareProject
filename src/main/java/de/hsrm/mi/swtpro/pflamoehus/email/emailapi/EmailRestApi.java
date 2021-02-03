package de.hsrm.mi.swtpro.pflamoehus.email.emailapi;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.CrossOrigin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import de.hsrm.mi.swtpro.pflamoehus.email.RequestedPasswordResets;
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

    RequestedPasswordResets rpr = new RequestedPasswordResets();

    Logger logger = LoggerFactory.getLogger(EmailRestApi.class);
    
    /**
     * Try to send Email.
     * 
     * @param email Email of the receiver.
     * @return Returns whether the email was sent successfully.
     */
    @PostMapping("/send")
    public boolean sendEmail(@RequestBody String email) {

        String adr = email.replace("\"", "");
        rpr.addPasswordRequest(adr);
        String code = rpr.getCode(adr);
        
        String topic = "Passwort zurücksetzen im Pflamoehus!";
        String link = "http://localhost:8080/resetPassword/" + adr + "/" + code;
        String text = "Guten Tag! über folgenden Link kannst du dein Passwort zurücksetzen: " + link;

        try {
            emailservice.sendEmail(email, text, topic);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @GetMapping("/getCode/{email}")
    public String getCode(@PathVariable String email) {
        String code = rpr.getCode(email);
        return code;
    }
}
