package de.hsrm.mi.swtpro.pflamoehus.user.paymentmethods.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.persistence.OptimisticLockException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import de.hsrm.mi.swtpro.pflamoehus.exceptions.service.CreditcardServiceException;
import de.hsrm.mi.swtpro.pflamoehus.user.User;
import de.hsrm.mi.swtpro.pflamoehus.user.paymentmethods.Creditcard;
import de.hsrm.mi.swtpro.pflamoehus.user.paymentmethods.CreditcardRepository;
import de.hsrm.mi.swtpro.pflamoehus.user.userservice.UserService;

@Service
public class CreditcardServiceImpl implements CreditcardService {

    private static final Logger ORDERDETAILSSERVICELOGGER = LoggerFactory.getLogger(CreditcardServiceImpl.class);

    @Autowired
    CreditcardRepository creditcardRepo;

    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder pe;

    @Override
    public List<Creditcard> findByDateOfExpiry(LocalDate expiry) {
        return creditcardRepo.findByDateOfExpiry(expiry);
    }

    @Override
    public Optional<Creditcard> findById(long id) {
        return creditcardRepo.findById(id);
    }

    @Override
    public Creditcard saveCreditcard(Creditcard card) {
       try{
        card = creditcardRepo.save(card);
       }catch(OptimisticLockException ole){
        ORDERDETAILSSERVICELOGGER.error("Creditcards can only be edited by one person at a time.");
        throw new CreditcardServiceException();
       }

       return card;
    }

    @Override
    public void deleteCreditcard(long id) {
        Optional<Creditcard> cc = findById(id);
        creditcardRepo.delete(cc.get());

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
