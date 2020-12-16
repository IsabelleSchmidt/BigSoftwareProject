package de.hsrm.mi.swtpro.pflamoehus.user.paymentmethods;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditcardRepository extends JpaRepository<Creditcard, Long> {

    <List> Creditcard findByDateOfExpiry(LocalDate dateOfExpiry); // so you can implement a function which looks for
                                                                  // expired CreditCards so you have to get a new
                                                                  // PaymentMethod

    Optional<Creditcard> findById(long Id);
}
