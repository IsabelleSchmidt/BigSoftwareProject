package de.hsrm.mi.swtpro.pflamoehus.email;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordRequestRepository extends JpaRepository<PasswordRequest,Long> {
    
    Optional<PasswordRequest> findByEmail(String email);
}
