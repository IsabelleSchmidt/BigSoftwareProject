package de.hsrm.mi.swtpro.pflamoehus.user.paymentmethods;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditcardRepository extends JpaRepository<Creditcard, Long> {
    Creditcard findByOwner (String ower);
    //so you can implement a function which look for expired CreditCards so you have to get a new PaymentMethod
    <List>Creditcard findByDateOfExpiry(LocalDate dateOfExpiry);
    Optional<Creditcard> findById(long Id);   
}
