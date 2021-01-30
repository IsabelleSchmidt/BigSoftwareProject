package de.hsrm.mi.swtpro.pflamoehus.email.emailservice;

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
    
}
