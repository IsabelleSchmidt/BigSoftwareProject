package de.hsrm.mi.swtpro.pflamoehus.order.status;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

/*
 * Status-Repository for different operations to apply on the database.
 * 
 * @author Ann-Cathrin Fabian
 * @version 1
 */
public interface StatusRepository extends JpaRepository<Status, Long> {

    /**
     * Find a status by its code.
     * 
     * @param status wanted status
     * @return status
     */
    Optional<Status> findByStatuscode(Statuscode status);
    
}
