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
import de.hsrm.mi.swtpro.pflamoehus.exceptions.service.ProductServiceException;

@Service
public class PasswordRequestServiceImpl implements PasswordRequestService {

    @Autowired
    PasswordRequestRepository passwordRequestRepo;

    @Override
    public PasswordRequest searchRequestWithEmail(String email) {
        Optional<PasswordRequest> request = passwordRequestRepo.findByEmail(email);
        if (request.isEmpty()) {
            // TODO: eigene exception
            throw new ProductServiceException("Could not find code with given mail");
        }
        return request.get();
    }

    @Transactional
    public void saveNewRequest(String email) {
        PasswordRequest pr = new PasswordRequest(email, getRandomString());
        try {
            passwordRequestRepo.save(pr);
        } catch (OptimisticLockException ole) {
            // TODO: logger output
        }

    }

    @Override
    public List<PasswordRequest> findAll() {
        return passwordRequestRepo.findAll();
    }

    @Override
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
    }

    

