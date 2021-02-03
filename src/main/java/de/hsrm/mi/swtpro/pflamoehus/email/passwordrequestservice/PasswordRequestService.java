package de.hsrm.mi.swtpro.pflamoehus.email.passwordrequestservice;

import java.util.List;

import de.hsrm.mi.swtpro.pflamoehus.email.PasswordRequest;
/*
 * PasswordRequestService for different operations to apply on the passwordrequests.
 * 
 * @author Svenja Schenk, Sarah Wenzel
 * @version 1
 */
public interface PasswordRequestService {
    
    /**
     * Search the passwordrequest to an email.
     * 
     * @param email to get the matching passwordrequest.
     * @return passwordrequest
     */
    public PasswordRequest searchRequestWithEmail(String email);

    /**
     * Find all passwordrequest.
     * 
     * @return list of passwordrequests
     */
    public List<PasswordRequest> findAll();

    /**
     * Creates a random string.
     * 
     * @return random string.
     */
    public String getRandomString();

    /**
     * Save an new passwordrequest into the database.
     * 
     * @param email from the passwordrequest.
     */
    public void saveNewRequest(String email);

    /**
     * Delete items which have a timestamp older than 10min ago.
     */
    public void deleteOldItems();

}
