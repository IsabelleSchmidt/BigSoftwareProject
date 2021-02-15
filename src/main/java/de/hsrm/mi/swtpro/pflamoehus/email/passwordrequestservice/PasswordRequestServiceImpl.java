package de.hsrm.mi.swtpro.pflamoehus.email.passwordrequestservice;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import javax.persistence.OptimisticLockException;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import de.hsrm.mi.swtpro.pflamoehus.email.PasswordRequest;
import de.hsrm.mi.swtpro.pflamoehus.email.PasswordRequestRepository;
import de.hsrm.mi.swtpro.pflamoehus.exceptions.service.EmailServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * PasswordRequestServiceImpl for implementing the interface 'PasswordRequestService'.
 * 
 * @author Svenja Schenk, Sarah Wenzel
 * @version 1
 */
@Service
public class PasswordRequestServiceImpl implements PasswordRequestService {

    @Autowired
    PasswordRequestRepository passwordRequestRepo;

    Logger logger = LoggerFactory.getLogger(PasswordRequestServiceImpl.class);

    /**
     * Returns the passwordreuest to an email written in the database.
     * 
     * @param email to get the passwordrequest from an email.
     * @return passwordrequest
     * 
     */
    @Override
    public PasswordRequest searchRequestWithEmail(String email) {
        Optional<PasswordRequest> request = passwordRequestRepo.findByEmail(email);
        if (request.isEmpty()) {
            throw new EmailServiceException("Could not find code with given mail");
        }
        return request.get();
    }

    /**
     * Saves a new passwordrequest into the database.
     * 
     * @param email email from the request.
     */
    @Transactional
    public void saveNewRequest(String email) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Long milliseconds = timestamp.getTime();

        PasswordRequest pr = new PasswordRequest();
        pr.setEmail(email);
        pr.setCode(getRandomString());
        pr.setTimestamp(milliseconds);

        try {
            passwordRequestRepo.save(pr);
        } catch (OptimisticLockException ole) {
            throw new EmailServiceException("Could not save new Password Request");
        }
    }

    /**
     * Returns a list of all passwordrequests written in the database.
     * 
     * @return list of passwordrequests
     */
    @Override
    public List<PasswordRequest> findAll() {
        return passwordRequestRepo.findAll();
    }

    /**
     * Returns a random string.
     * 
     * @return random String
     */
    @Override
    public String getRandomString() {
        String result = "";
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        int charactersLength = characters.length();
        int resultLength = (int) (Math.random() * (20 - 10) + 10);

        for (int i = 0; i < resultLength; i++) {
            result += characters.charAt((int)(Math.random() * charactersLength));
        }

        return result;
    }

    /**
     * Removes passwordrequests which are longer then 10min ago from the database.
     * 
     */
    @Override
    public void deleteOldItems() {

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Long milliseconds = timestamp.getTime();
        Long alloweddelay = (long) 600000; // 10min delay
        Long maxTime = milliseconds + alloweddelay;
        passwordRequestRepo.removeOlderThan(maxTime);
    }
}

    

