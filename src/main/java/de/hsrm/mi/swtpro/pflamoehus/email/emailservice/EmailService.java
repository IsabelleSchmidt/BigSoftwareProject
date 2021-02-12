package de.hsrm.mi.swtpro.pflamoehus.email.emailservice;



import javax.mail.MessagingException;
import java.io.IOException;
import de.hsrm.mi.swtpro.pflamoehus.order.Order;
import de.hsrm.mi.swtpro.pflamoehus.user.User;

/*
 * EmailService to send Emails.
 * 
 * @author Sarah Wenzel
 * @version 1
 */
public interface EmailService {

    /**
     * @param to The receiver of the email.
     * @param body The message of the email.
     * @param topic The title of the email.
     * 
     */
    void sendEmail(String to, String body, String topic);


   void sendHTMLmail(Order order, User user) throws MessagingException, IOException;
    
}
