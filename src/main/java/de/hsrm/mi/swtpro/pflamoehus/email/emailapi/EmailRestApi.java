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

import de.hsrm.mi.swtpro.pflamoehus.email.emailservice.EmailService;
import de.hsrm.mi.swtpro.pflamoehus.email.passwordrequestservice.PasswordRequestService;

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

    
    @Autowired PasswordRequestService passwordRequestService;

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

        //delete old requests
        passwordRequestService.deleteOldItems();
        
        //save new request
        passwordRequestService.saveNewRequest(adr);

        // rpr.addPasswordRequest(adr);
        //String code = rpr.getCode(adr);
        String code = passwordRequestService.searchRequestWithEmail(adr).getCode();
        Long ts = passwordRequestService.searchRequestWithEmail(adr).getTimestamp();


        String topic = "Passwort zurücksetzen im Pflamoehus!";
        String link = "http://localhost:8080/resetPassword/" + adr + "/" + code + "$" + ts;
        String text = "Guten Tag! über folgenden Link kannst du dein Passwort zurücksetzen: " + link;

        try {
            emailservice.sendEmail(email, text, topic);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Get code of a request to change the password.
     * 
     * @param email Email of the request.
     * @return Returns the code that includes the timestamp.
     */
    @GetMapping("/getCode/{email}")
    public String getCode(@PathVariable String email) {
       
        String code = passwordRequestService.searchRequestWithEmail(email).getCode() + "$" + passwordRequestService.searchRequestWithEmail(email).getTimestamp();
        return code;
    }
}
