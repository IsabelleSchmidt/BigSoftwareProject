package de.hsrm.mi.swtpro.pflamoehus.email.emailservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/*
 * EmailServiceImpl for implementing the interface 'EmailService'.
 * 
 * @author Sarah Wenzel
 * @version 1
 */
@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender javamailsender;


    /**
     * Sends an email from pflamoehus@gmail.com.
     * 
     * @param to The receiver of the email.
     * @param body The message of the email.
     * @param topic The title of the email.
     * 
     */
    @Override
    public void sendEmail(String to, String body, String topic) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("pflamoehus@gmail.com");
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(topic);
        simpleMailMessage.setText(body);
        javamailsender.send(simpleMailMessage);
    }
    
}
