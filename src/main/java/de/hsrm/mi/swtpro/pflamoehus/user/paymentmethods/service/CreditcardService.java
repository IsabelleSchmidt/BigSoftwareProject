package de.hsrm.mi.swtpro.pflamoehus.user.paymentmethods.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import de.hsrm.mi.swtpro.pflamoehus.user.paymentmethods.Creditcard;

public interface CreditcardService {
    
    List<Creditcard> findByDateOfExpiry (LocalDate expiry);

    Optional<Creditcard> findById(long id);

    Creditcard saveCreditcard (Creditcard card);

    void deleteCreditcard (long id);

    String encodeCardNumber(String cardnumber);

}
