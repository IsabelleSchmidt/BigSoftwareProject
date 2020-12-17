package de.hsrm.mi.swtpro.pflamoehus.user.paymentmethods;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

/*
 * Creditcard-Repository for different operations within the database.
 * 
 * @author Ann-Cathrin Fabian
 * @version 1
 */
 public interface CreditcardRepository extends JpaRepository<Creditcard, Long> {
    /**
     * Finds all creditcards with a certain date of expiry. It's usefull for finding expired creditcards so the user has
     * to choose a new payment method.
     * 
     * @param expiry wanted date of expiry
     * @return list of creditcards
     */
    List<Creditcard> findByDateOfExpiry(LocalDate expiry);
    
    /**
     * Find a creditcard by its id.
     * 
     * @param id wanted id
     * @return optional of type creditcard
     */
    Optional<Creditcard> findById(long id);
}
