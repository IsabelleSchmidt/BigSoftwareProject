package de.hsrm.mi.swtpro.pflamoehus.user.paymentmethods.paymentservice;

import java.util.Optional;
import de.hsrm.mi.swtpro.pflamoehus.user.paymentmethods.Bankcard;

/*
 * BankcardService for different operations to apply on bankcard.
 * 
 * @author Ann-Cathrin Fabian
 * @version 1
 */
public interface BankcardService {

    /** 
     * Find a bankcard by its id.
     * 
     * @param id from the bankcard
     * @return Bankcard
     */
    Optional<Bankcard> findById(long id);

    /** 
     * Save bankcard in repository.
     * 
     * @param card that should get saved
     * @return Bankcard
     */
    Bankcard saveBankcard(Bankcard card);

    /** 
     * Delete one bankcard.
     * 
     * @param id bankcard, that should get deleted
     */
    void deleteBankcard(long id);

    /**
     * When creating a new user or editing the iban, the new iban has to get
     * encoded.
     * 
     * @param iban to be encoded
     * @return string
     */
    String encodeIBAN(String iban);

    
    
}
