package de.hsrm.mi.swtpro.pflamoehus.user.paymentmethods;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditcardRepository extends JpaRepository<Creditcard, Long> {
    //so you can implement a function which look for expired CreditCards so you have to get a new PaymentMethod
    List<Creditcard> findByDateOfExpiry(LocalDate expiry);
    Optional<Creditcard> findById(long Id);   

}
