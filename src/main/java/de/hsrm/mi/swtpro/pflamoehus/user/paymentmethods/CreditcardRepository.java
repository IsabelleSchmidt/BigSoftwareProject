package de.hsrm.mi.swtpro.pflamoehus.user.paymentmethods;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditcardRepository extends JpaRepository<Creditcard, Long> {
    Creditcard findByOwner (String ower);
}
