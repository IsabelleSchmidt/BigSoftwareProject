package de.hsrm.mi.swtpro.pflamoehus.email.emailservice;
import java.util.HashMap;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

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

    @Autowired
    private SpringTemplateEngine templateEngine;

    Logger LOGGER = LoggerFactory.getLogger(EmailServiceImpl.class);

    
    /**
     * Sends an email from pflamoehus@mi.de
     * 
     * @param to The receiver of the email.
     * @param body The message of the email.
     * @param topic The title of the email.
     * 
     */
    @Override
    public void sendEmail(String to, String body, String topic) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("pflamoehus@mi.de");
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(topic);
        simpleMailMessage.setText(body);
        javamailsender.send(simpleMailMessage);
       LOGGER.info("Standard Email gesendet.");
    }

    /**
     * Sends an order confirmation email from pflamoehus@mi.de
     * The email contains a dynamically rendered html invoice
     * @param order the order that this confirmation email is for
     * @param user  the user that placed the order and receives this email
     */
    @Override
    @Transactional
    public void sendHTMLmail( HashMap<String,Object> contextdata, String to, String from, String topic, String templatelocation) throws MessagingException {
    
        MimeMessage message = javamailsender.createMimeMessage(); //allows to use attachment to emails
        MimeMessageHelper helper = new MimeMessageHelper(message,true, "UTF-8");
      
        Context context = new Context(); //thymeleaf template context
        context.setVariables(contextdata);//add previous defined data to the context
        
        String html = templateEngine.process(templatelocation, context); //process the html file
        helper.setTo(to);
        helper.setText(html, true);
        helper.setSubject(topic);
        helper.setFrom(from);
        
        javamailsender.send(message);
        LOGGER.info("HTML Email gesendet.");
    }

 

    
}
