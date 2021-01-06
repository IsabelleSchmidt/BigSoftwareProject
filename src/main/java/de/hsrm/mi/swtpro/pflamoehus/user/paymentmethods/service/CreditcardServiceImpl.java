package de.hsrm.mi.swtpro.pflamoehus.user.paymentmethods.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.persistence.OptimisticLockException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.hsrm.mi.swtpro.pflamoehus.exceptions.CreditcardServiceException;
import de.hsrm.mi.swtpro.pflamoehus.user.paymentmethods.Creditcard;
import de.hsrm.mi.swtpro.pflamoehus.user.paymentmethods.CreditcardRepository;

@Service
public class CreditcardServiceImpl implements CreditcardService {

    private static final Logger ORDERDETAILSSERVICELOGGER = LoggerFactory.getLogger(CreditcardServiceImpl.class);

    @Autowired
    CreditcardRepository creditcardRepo;

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
    
}
