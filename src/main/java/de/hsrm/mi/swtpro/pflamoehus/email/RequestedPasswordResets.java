package de.hsrm.mi.swtpro.pflamoehus.email;

import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;

import de.hsrm.mi.swtpro.pflamoehus.email.emailservice.EmailService;
import java.sql.Timestamp;

/*
* Stores the RequestedPasswordResets.
*
* @author Sarah Wenzel
* @version 1
*/
public class RequestedPasswordResets {
    
    ArrayList<PasswordRequest> requests = new ArrayList<>();

    @Autowired 
    EmailService emailservice;

    Logger logger = LoggerFactory.getLogger(RequestedPasswordResets.class);

    /** 
    * Adds a new request.
    *
    * @param email that requests to change the password.
    */
    public void addPasswordRequest(String email) {
        PasswordRequest pr = new PasswordRequest(email, getRandomString());
        for (int i = 0; i < requests.size(); i++) {
            if (requests.get(i).getEmail().equals(email)) {
                requests.remove(i);
            }
        }
        requests.add(pr);
    }

    /** 
    * Gets the code from an email.
    *
    * @param email to get the code from.
    */
    public String getCode(String email) {
        String c = "";
        for (int i = 0; i < requests.size(); i++) {
            if (requests.get(i).getEmail().equals(email)) {
                c = requests.get(i).getCode();
            }
        }
        return c;
    }

    /**
     * Generate a random string.
     * 
     * @return the generated random string
     */
    public String getRandomString() {
        String result = "";
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        int charactersLength = characters.length();
        int resultLength = (int) (Math.random() * (20 - 10) + 10);

        for (int i = 0; i < resultLength; i++) {
            result += characters.charAt((int)(Math.random() * charactersLength));
        }

        String timecode = "";

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        timecode += timestamp.getTime();

        result += "$" + timecode;

        return result;
    }

    /** 
	 * Get all requests.
	 * 
	 * @return ArrayList<PasswordRequest>
	 */
    public ArrayList<PasswordRequest> getRequests() {
        return requests;
    }

    /** 
	 * Set Requests. 
	 * 
	 * @param requests to be set
	 */
    public void setRequests(ArrayList<PasswordRequest> requests) {
        this.requests = requests;
    }

}
