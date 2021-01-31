package de.hsrm.mi.swtpro.pflamoehus.security.jwt.JwtStore;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JwtStoreRepository extends JpaRepository<JwtStore, Long>{

    boolean existsByToken (String token);

    List<JwtStore> findAll();

    Optional<JwtStore> findJwtStoreById(Long id);

    Optional<JwtStore> findByToken(String token);
    
}
