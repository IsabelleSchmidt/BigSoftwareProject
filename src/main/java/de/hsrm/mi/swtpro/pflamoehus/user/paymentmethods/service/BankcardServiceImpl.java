package de.hsrm.mi.swtpro.pflamoehus.user.paymentmethods.service;

import java.util.Optional;
import java.util.Set;

import javax.persistence.OptimisticLockException;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import de.hsrm.mi.swtpro.pflamoehus.exceptions.service.BankcardServiceException;
import de.hsrm.mi.swtpro.pflamoehus.user.User;
import de.hsrm.mi.swtpro.pflamoehus.user.paymentmethods.Bankcard;
import de.hsrm.mi.swtpro.pflamoehus.user.paymentmethods.BankcardRepository;
import de.hsrm.mi.swtpro.pflamoehus.user.userservice.UserService;

@Service
public class BankcardServiceImpl implements BankcardService {

    @Autowired
    BankcardRepository bankcardRepo;

    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder pe;

    private static final Logger BANKCARDSERVICELOGGER = LoggerFactory.getLogger(BankcardServiceImpl.class);

    @Override
    public Optional<Bankcard> findById(long id) {
        return bankcardRepo.findById(id);
    }

    @Override
    public Bankcard saveBankcard(Bankcard card) {
        try{
            card = bankcardRepo.save(card);
        }catch(OptimisticLockException ole){
            BANKCARDSERVICELOGGER.error("Bankcards can only be edited by one person at a time.");
            throw new BankcardServiceException();
        }
        return card;
    }

    @Override
    public void deleteBankcard(long id) {
        Optional<Bankcard> b = findById(id);
        bankcardRepo.delete(b.get());
    }

     

     /**
     * When creating a new user or editing the iban, the new iban has to get
     * encoded.
     * 
     * @param iban to be encoded
     */
    @Override
    public String encodeIBAN(String iban) {

        iban = pe.encode(iban);
        return iban;
    }

    
    
}
