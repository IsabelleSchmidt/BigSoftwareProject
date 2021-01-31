package de.hsrm.mi.swtpro.pflamoehus.user.paymentmethods.paymentservice;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.persistence.OptimisticLockException;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import de.hsrm.mi.swtpro.pflamoehus.exceptions.service.CreditcardServiceException;
import de.hsrm.mi.swtpro.pflamoehus.user.paymentmethods.Creditcard;
import de.hsrm.mi.swtpro.pflamoehus.user.paymentmethods.CreditcardRepository;
import de.hsrm.mi.swtpro.pflamoehus.user.userservice.UserService;

/*
 * CreditcardServiceImpl for implementing the interface 'CreditcardService'.
 * 
 * @author Ann-Cathrin Fabian
 * @version 1
 */
@Service
public class CreditcardServiceImpl implements CreditcardService {

    private static final Logger ORDERDETAILSSERVICELOGGER = LoggerFactory.getLogger(CreditcardServiceImpl.class);

    @Autowired
    CreditcardRepository creditcardRepo;

    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder pe;

    
    /** 
     * Find all creditcards with a certain date of expiry.
     * 
     * @param expiry date of expiry
     * @return all creditcards
     */
    @Override
    public List<Creditcard> findByDateOfExpiry(LocalDate expiry) {
        return creditcardRepo.findByDateOfExpiry(expiry);
    }

    
    /** 
     * Find a creditcard by its id.
     * 
     * @param id from creditcard
     * @return creditcard
     */
    @Override
    public Optional<Creditcard> findById(long id) {
        return creditcardRepo.findById(id);
    }

    
    /** 
     * Save one creditcard.
     * 
     * @param card to be saved
     * @return Creditcard
     */
    @Override
    @Transactional
    public Creditcard saveCreditcard(Creditcard card) {
       try{
        card = creditcardRepo.save(card);

       }catch(OptimisticLockException ole){
        ORDERDETAILSSERVICELOGGER.error("Creditcards can only be edited by one person at a time.");
        throw new CreditcardServiceException();
       }
       return card;
    }

    
    /** 
     * Delete one creditcard.
     * 
     * @param id creditcard to be deleted
     */
    @Override
    @Transactional
    public void deleteCreditcard(long id) {
        Optional<Creditcard> cc = findById(id);
        if(cc.isPresent()){
            creditcardRepo.delete(cc.get());
        }
        

    }

    /**
     * When creating a new user or editing the creditcard number, the new number has
     * to get encoded.
     * 
     * @param cardnumber to be encoded
     * @return String
     */
    @Override
    public String encodeCardNumber(String cardnumber) {
       
        return pe.encode(cardnumber);
        
          
    }
    
}
