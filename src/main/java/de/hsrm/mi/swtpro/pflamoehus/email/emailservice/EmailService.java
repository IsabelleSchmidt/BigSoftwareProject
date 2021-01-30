package de.hsrm.mi.swtpro.pflamoehus.email.emailservice;

public interface EmailService {

    void sendEmail(String to, String body, String topic);
    
}
