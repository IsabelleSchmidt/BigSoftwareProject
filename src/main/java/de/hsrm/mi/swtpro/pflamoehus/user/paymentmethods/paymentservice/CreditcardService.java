package de.hsrm.mi.swtpro.pflamoehus.user.paymentmethods.paymentservice;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import de.hsrm.mi.swtpro.pflamoehus.user.paymentmethods.Creditcard;

/*
 * CreditcardService for different operations to apply on creditcard.
 * 
 * @author Ann-Cathrin Fabian
 * @version 1
 */
public interface CreditcardService {

    /**
     * Find all creditcards with a certain date of expiry.
     * 
     * @param expiry date of expiry
     * @return all creditcards
     */
    List<Creditcard> findByDateOfExpiry(LocalDate expiry);

    /**
     * Find a creditcard by its id.
     * 
     * @param id from creditcard
     * @return creditcard
     */
    Optional<Creditcard> findById(long id);

    /**
     * Save one creditcard.
     * 
     * @param card to be saved
     * @return Creditcard
     */
    Creditcard saveCreditcard(Creditcard card);

    /**
     * Delete one creditcard.
     * 
     * @param id creditcard to be deleted
     */
    void deleteCreditcard(long id);

    /**
     * When creating a new user or editing the creditcard number, the new number has
     * to get encoded.
     * 
     * @param cardnumber to be encoded
     * @return String
     */
    String encodeCardNumber(String cardnumber);

}
