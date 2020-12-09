package de.hsrm.mi.swtpro.pflamoehus.user.paymentmethods;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BankcardRepository extends JpaRepository<Bankcard, Long> {

    Optional<Bankcard> findById(long id);
}
