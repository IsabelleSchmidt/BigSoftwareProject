package de.hsrm.mi.swtpro.pflamoehus.email.emailservice;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.HashMap;

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

    /**
     * Sends an Email with embedded html template
     * @param context context needed to fill out a dynamic html template
     * @param template the html template that's sent with the mail
     * @param to recipient of the mail
     * @param from sender of the mail
     * @param topic topic of the mail
     * 
     */
   void sendHTMLmail(HashMap<String,Object> contextdata,String to, String from, String topic, String templatelocation) throws MessagingException, IOException;
    
}
