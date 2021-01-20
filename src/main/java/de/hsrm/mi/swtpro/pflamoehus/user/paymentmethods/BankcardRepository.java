package de.hsrm.mi.swtpro.pflamoehus.user.paymentmethods;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

/*
 * Bankcard-Repository for different operations within the databse.
 * 
 * @author Ann-Cathrin Fabian
 * @version 1
 */
 public interface BankcardRepository extends JpaRepository<Bankcard, Long> {

    /**
     * Find a bankcard by its id.
     * 
     * @param id wanted id
     * @return optional of type bankcard
     */
    Optional<Bankcard> findById(long id);
}
