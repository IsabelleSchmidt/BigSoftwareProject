package de.hsrm.mi.swtpro.pflamoehus.email;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PasswordRequestRepository extends JpaRepository<PasswordRequest,Long> {
    
    Optional<PasswordRequest> findByEmail(String email);

    @Modifying
    @Transactional
    @Query("DELETE FROM PasswordRequest p WHERE p.timestamp <= :date")
    int removeOlderThan(@Param("date") Long date);
}
